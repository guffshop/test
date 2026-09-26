package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.LessonProgressDao
import com.example.data.local.dao.StudentProfileDao
import com.example.data.local.dao.SupportTicketDao
import com.example.data.local.entity.LessonProgressEntity
import com.example.data.local.entity.StudentProfileEntity
import com.example.data.local.entity.SupportTicketEntity

@Database(
    entities = [
        StudentProfileEntity::class,
        LessonProgressEntity::class,
        SupportTicketEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentProfileDao(): StudentProfileDao
    abstract fun lessonProgressDao(): LessonProgressDao
    abstract fun supportTicketDao(): SupportTicketDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "creative_masterclass.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
