package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_profile")
data class StudentProfileEntity(
    @PrimaryKey val email: String,
    val name: String,
    val status: String,
    val tier: String,
    val sheetSource: String,
    val lastLoginTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "lesson_progress")
data class LessonProgressEntity(
    @PrimaryKey val lessonId: String,
    val classId: String,
    val isCompleted: Boolean = false,
    val isBookmarked: Boolean = false,
    val lastPositionSeconds: Int = 0,
    val userNotes: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "support_tickets")
data class SupportTicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentEmail: String,
    val studentName: String,
    val category: String,
    val subject: String,
    val message: String,
    val status: String = "Mentor Reviewing",
    val mentorReply: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
