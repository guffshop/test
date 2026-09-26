package com.example.data.model

data class StudentUser(
    val email: String,
    val name: String,
    val passcode: String, // PIN or password specified in sheet
    val status: String = "Active", // "Active", "Inactive", "Suspended"
    val tier: String = "VIP All-Access", // e.g. "VIP All-Access", "Photoshop Only"
    val enrolledCategories: List<String> = listOf("all"),
    val joinDate: String = "2026",
    val sheetSource: String = "Google Sheets Enrollment"
) {
    val isActive: Boolean
        get() = status.equals("Active", ignoreCase = true) || status.equals("Enrolled", ignoreCase = true) || status.equals("Approved", ignoreCase = true)

    fun hasAccessToCategory(category: CategoryType): Boolean {
        if (enrolledCategories.any { it.equals("all", ignoreCase = true) }) return true
        return enrolledCategories.any { it.equals(category.id, ignoreCase = true) || it.contains(category.title, ignoreCase = true) }
    }
}
