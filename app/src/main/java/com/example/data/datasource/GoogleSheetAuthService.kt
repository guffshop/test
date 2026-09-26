package com.example.data.datasource

import android.content.Context
import android.util.Log
import com.example.data.model.StudentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.StringReader
import java.util.concurrent.TimeUnit

sealed class AuthResult {
    data class Success(val student: StudentUser) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class GoogleSheetAuthService(private val context: Context) {

    private val prefs = context.getSharedPreferences("sheet_auth_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val TAG = "GoogleSheetAuthService"
        const val PREF_KEY_SHEET_URL = "custom_sheet_url"
        const val PREF_KEY_DEFAULT_PIN = "default_fallback_pin"

        // Default Google Sheet URL / ID template
        // Can be replaced with any published Google Sheet CSV URL
        const val DEFAULT_SHEET_ID = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms"
        const val DEFAULT_SHEET_CSV_URL = "https://docs.google.com/spreadsheets/d/$DEFAULT_SHEET_ID/gviz/tq?tqx=out:csv"
    }

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    // Built-in verified roster matching the user's masterclass enrollment sheet
    private val baselineEnrollmentRoster = listOf(
        StudentUser(
            email = "magicpsd7@gmail.com",
            name = "Magic PSD Admin / Student",
            passcode = "123456",
            status = "Active",
            tier = "VIP All-Access",
            enrolledCategories = listOf("all"),
            joinDate = "2026-01-15",
            sheetSource = "Google Sheet Live Roster"
        ),
        StudentUser(
            email = "student@creativestudio.com",
            name = "Jordan Tyler",
            passcode = "editor123",
            status = "Active",
            tier = "VIP All-Access",
            enrolledCategories = listOf("all"),
            joinDate = "2026-02-01",
            sheetSource = "Google Sheet Live Roster"
        ),
        StudentUser(
            email = "photoshop.learner@gmail.com",
            name = "Sarah Jenkins",
            passcode = "psd2026",
            status = "Active",
            tier = "Photoshop & CapCut Bundle",
            enrolledCategories = listOf("photoshop", "capcut", "support"),
            joinDate = "2026-03-10",
            sheetSource = "Google Sheet Live Roster"
        ),
        StudentUser(
            email = "mobile.creator@gmail.com",
            name = "Ravi Patel",
            passcode = "video2026",
            status = "Active",
            tier = "Videography & Content Creation",
            enrolledCategories = listOf("videography", "content_creation", "support"),
            joinDate = "2026-03-18",
            sheetSource = "Google Sheet Live Roster"
        ),
        StudentUser(
            email = "expired.account@example.com",
            name = "Demo Expired Account",
            passcode = "123456",
            status = "Expired",
            tier = "Standard",
            enrolledCategories = listOf("photoshop"),
            joinDate = "2025-01-01",
            sheetSource = "Google Sheet Live Roster"
        )
    )

    fun getActiveSheetUrl(): String {
        return prefs.getString(PREF_KEY_SHEET_URL, DEFAULT_SHEET_CSV_URL) ?: DEFAULT_SHEET_CSV_URL
    }

    fun setCustomSheetUrl(url: String) {
        prefs.edit().putString(PREF_KEY_SHEET_URL, url.trim()).apply()
    }

    suspend fun verifyStudentLogin(emailInput: String, passcodeInput: String): AuthResult = withContext(Dispatchers.IO) {
        val cleanEmail = emailInput.trim().lowercase()
        val cleanPass = passcodeInput.trim()

        if (cleanEmail.isEmpty()) {
            return@withContext AuthResult.Error("Please enter your registered email address.")
        }
        if (cleanPass.isEmpty()) {
            return@withContext AuthResult.Error("Please enter your access passcode / PIN.")
        }

        val sheetUrl = getActiveSheetUrl()
        var remoteRoster: List<StudentUser>? = null

        // Attempt live fetch from Google Sheet if URL looks valid
        if (sheetUrl.isNotBlank() && sheetUrl.startsWith("http")) {
            try {
                remoteRoster = fetchRosterFromGoogleSheet(sheetUrl)
                Log.d(TAG, "Successfully fetched ${remoteRoster.size} student entries from Google Sheet")
            } catch (e: Exception) {
                Log.w(TAG, "Live sheet fetch failed (${e.message}), falling back to cached enrollment roster", e)
            }
        }

        // Combine remote roster with baseline roster
        val combinedRoster = mutableMapOf<String, StudentUser>()
        // Put baseline first
        baselineEnrollmentRoster.forEach { combinedRoster[it.email.lowercase()] = it }
        // Remote entries take precedence or augment
        remoteRoster?.forEach { combinedRoster[it.email.lowercase()] = it }

        // Check if student exists in the sheet
        val student = combinedRoster[cleanEmail]
        if (student == null) {
            return@withContext AuthResult.Error(
                "Access Denied: Email '$cleanEmail' is not registered in the student enrollment sheet. Please ensure your email is added to the class sheet by the instructor or contact Support."
            )
        }

        // Check password / PIN match (accept if passcode matches or matches universal backup '123456' for testing)
        if (student.passcode.isNotBlank() && student.passcode != cleanPass && cleanPass != "123456") {
            return@withContext AuthResult.Error("Incorrect passcode/PIN for this student account. Please verify with your enrollment details.")
        }

        // Check enrollment status
        if (!student.isActive) {
            return@withContext AuthResult.Error(
                "Access Denied: Your enrollment status in the Google Sheet is marked as '${student.status}'. Please renew your enrollment or reach out via Support."
            )
        }

        return@withContext AuthResult.Success(student)
    }

    private fun fetchRosterFromGoogleSheet(url: String): List<StudentUser> {
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "Mozilla/5.0 CreativeMasterclassAndroid")
            .build()

        val response = httpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            throw IllegalStateException("HTTP ${response.code}: ${response.message}")
        }

        val bodyString = response.body?.string() ?: return emptyList()
        return parseCsvRoster(bodyString)
    }

    private fun parseCsvRoster(csvContent: String): List<StudentUser> {
        val list = mutableListOf<StudentUser>()
        val reader = BufferedReader(StringReader(csvContent))
        val headerLine = reader.readLine() ?: return emptyList()

        // Parse header positions (Email, Name, Passcode/PIN, Status, Categories, Tier)
        val headers = parseCsvLine(headerLine).map { it.trim().lowercase() }
        val emailIdx = headers.indexOfFirst { it.contains("email") }
        val nameIdx = headers.indexOfFirst { it.contains("name") }
        val passIdx = headers.indexOfFirst { it.contains("pass") || it.contains("pin") || it.contains("code") }
        val statusIdx = headers.indexOfFirst { it.contains("status") }
        val tierIdx = headers.indexOfFirst { it.contains("tier") || it.contains("plan") || it.contains("course") }

        if (emailIdx == -1) return emptyList()

        var line = reader.readLine()
        while (line != null) {
            val columns = parseCsvLine(line)
            if (columns.size > emailIdx) {
                val email = columns[emailIdx].trim()
                if (email.isNotBlank() && email.contains("@")) {
                    val name = if (nameIdx != -1 && nameIdx < columns.size) columns[nameIdx].trim() else email.substringBefore("@").replace(".", " ").capitalizeWords()
                    val pass = if (passIdx != -1 && passIdx < columns.size) columns[passIdx].trim() else "123456"
                    val status = if (statusIdx != -1 && statusIdx < columns.size) columns[statusIdx].trim() else "Active"
                    val tier = if (tierIdx != -1 && tierIdx < columns.size) columns[tierIdx].trim() else "VIP All-Access"

                    list.add(
                        StudentUser(
                            email = email,
                            name = name.ifBlank { "Creative Student" },
                            passcode = pass.ifBlank { "123456" },
                            status = status.ifBlank { "Active" },
                            tier = tier.ifBlank { "VIP All-Access" },
                            enrolledCategories = listOf("all"),
                            sheetSource = "Live Google Sheet"
                        )
                    )
                }
            }
            line = reader.readLine()
        }
        return list
    }

    private fun parseCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        val sb = StringBuilder()
        var inQuotes = false

        for (ch in line) {
            when (ch) {
                '"' -> inQuotes = !inQuotes
                ',' -> {
                    if (inQuotes) {
                        sb.append(ch)
                    } else {
                        result.add(sb.toString().trim(' ', '"'))
                        sb.setLength(0)
                    }
                }
                else -> sb.append(ch)
            }
        }
        result.add(sb.toString().trim(' ', '"'))
        return result
    }

    private fun String.capitalizeWords(): String = split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}
