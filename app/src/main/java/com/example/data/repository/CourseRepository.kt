package com.example.data.repository

import android.content.Context
import com.example.data.datasource.AuthResult
import com.example.data.datasource.GoogleSheetAuthService
import com.example.data.datasource.PredefinedClasses
import com.example.data.local.AppDatabase
import com.example.data.local.entity.LessonProgressEntity
import com.example.data.local.entity.StudentProfileEntity
import com.example.data.local.entity.SupportTicketEntity
import com.example.data.model.CategoryType
import com.example.data.model.CourseClass
import com.example.data.model.StudentUser
import com.example.data.model.VideoLesson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CourseRepository(context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val studentDao = db.studentProfileDao()
    private val progressDao = db.lessonProgressDao()
    private val ticketDao = db.supportTicketDao()
    private val sheetAuthService = GoogleSheetAuthService(context)

    // Current student flow
    val currentStudentFlow: Flow<StudentProfileEntity?> = studentDao.getCurrentStudentFlow()

    // All progress flow
    val allProgressFlow: Flow<Map<String, LessonProgressEntity>> = progressDao.getAllProgressFlow().map { list ->
        list.associateBy { it.lessonId }
    }

    // Support tickets flow
    val supportTicketsFlow: Flow<List<SupportTicketEntity>> = ticketDao.getAllTicketsFlow()

    fun getAllClasses(): List<CourseClass> = PredefinedClasses.allClasses

    fun getClassesForCategory(category: CategoryType): List<CourseClass> =
        PredefinedClasses.getClassesForCategory(category)

    fun getClassById(classId: String): CourseClass? = PredefinedClasses.getClassById(classId)

    fun getLessonById(classId: String, lessonId: String): VideoLesson? =
        PredefinedClasses.getLessonById(classId, lessonId)

    fun getActiveSheetUrl(): String = sheetAuthService.getActiveSheetUrl()

    fun setCustomSheetUrl(url: String) = sheetAuthService.setCustomSheetUrl(url)

    suspend fun verifyAndLoginStudent(email: String, passcode: String): AuthResult {
        val result = sheetAuthService.verifyStudentLogin(email, passcode)
        if (result is AuthResult.Success) {
            val entity = StudentProfileEntity(
                email = result.student.email,
                name = result.student.name,
                status = result.student.status,
                tier = result.student.tier,
                sheetSource = result.student.sheetSource,
                lastLoginTimestamp = System.currentTimeMillis()
            )
            studentDao.saveStudent(entity)
        }
        return result
    }

    suspend fun logout() {
        studentDao.clearStudent()
    }

    suspend fun toggleLessonCompleted(lessonId: String, classId: String, currentStatus: Boolean) {
        val existing = progressDao.getProgressForLesson(lessonId)
        if (existing == null) {
            progressDao.saveProgress(
                LessonProgressEntity(
                    lessonId = lessonId,
                    classId = classId,
                    isCompleted = !currentStatus
                )
            )
        } else {
            progressDao.updateCompletionStatus(lessonId, !currentStatus)
        }
    }

    suspend fun toggleBookmark(lessonId: String, classId: String, currentStatus: Boolean) {
        val existing = progressDao.getProgressForLesson(lessonId)
        if (existing == null) {
            progressDao.saveProgress(
                LessonProgressEntity(
                    lessonId = lessonId,
                    classId = classId,
                    isBookmarked = !currentStatus
                )
            )
        } else {
            progressDao.updateBookmark(lessonId, !currentStatus)
        }
    }

    suspend fun saveLessonNotes(lessonId: String, classId: String, notes: String) {
        val existing = progressDao.getProgressForLesson(lessonId)
        if (existing == null) {
            progressDao.saveProgress(
                LessonProgressEntity(
                    lessonId = lessonId,
                    classId = classId,
                    userNotes = notes
                )
            )
        } else {
            progressDao.updateNotes(lessonId, notes)
        }
    }

    fun getProgressForLessonFlow(lessonId: String): Flow<LessonProgressEntity?> {
        return progressDao.getProgressForLessonFlow(lessonId)
    }

    suspend fun submitSupportTicket(
        studentEmail: String,
        studentName: String,
        category: String,
        subject: String,
        message: String
    ): Long {
        val ticket = SupportTicketEntity(
            studentEmail = studentEmail,
            studentName = studentName,
            category = category,
            subject = subject,
            message = message,
            status = "Mentor Reviewing",
            mentorReply = "Thanks for submitting your question! Lead mentor Alex/Jordan will review your project and reply within 1-2 hours."
        )
        return ticketDao.insertTicket(ticket)
    }
}
