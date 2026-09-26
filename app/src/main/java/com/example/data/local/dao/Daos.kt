package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.LessonProgressEntity
import com.example.data.local.entity.StudentProfileEntity
import com.example.data.local.entity.SupportTicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentProfileDao {
    @Query("SELECT * FROM student_profile LIMIT 1")
    fun getCurrentStudentFlow(): Flow<StudentProfileEntity?>

    @Query("SELECT * FROM student_profile LIMIT 1")
    suspend fun getCurrentStudent(): StudentProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStudent(student: StudentProfileEntity)

    @Query("DELETE FROM student_profile")
    suspend fun clearStudent()
}

@Dao
interface LessonProgressDao {
    @Query("SELECT * FROM lesson_progress")
    fun getAllProgressFlow(): Flow<List<LessonProgressEntity>>

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId LIMIT 1")
    fun getProgressForLessonFlow(lessonId: String): Flow<LessonProgressEntity?>

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId LIMIT 1")
    suspend fun getProgressForLesson(lessonId: String): LessonProgressEntity?

    @Query("SELECT * FROM lesson_progress WHERE classId = :classId")
    fun getProgressForClassFlow(classId: String): Flow<List<LessonProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(progress: LessonProgressEntity)

    @Query("UPDATE lesson_progress SET isCompleted = :completed, updatedAt = :timestamp WHERE lessonId = :lessonId")
    suspend fun updateCompletionStatus(lessonId: String, completed: Boolean, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE lesson_progress SET isBookmarked = :bookmarked WHERE lessonId = :lessonId")
    suspend fun updateBookmark(lessonId: String, bookmarked: Boolean)

    @Query("UPDATE lesson_progress SET userNotes = :notes WHERE lessonId = :lessonId")
    suspend fun updateNotes(lessonId: String, notes: String)
}

@Dao
interface SupportTicketDao {
    @Query("SELECT * FROM support_tickets ORDER BY createdAt DESC")
    fun getAllTicketsFlow(): Flow<List<SupportTicketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: SupportTicketEntity): Long

    @Query("DELETE FROM support_tickets WHERE id = :ticketId")
    suspend fun deleteTicket(ticketId: Long)
}
