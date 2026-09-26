package com.example.data.model

data class CourseClass(
    val id: String,
    val category: CategoryType,
    val title: String,
    val subtitle: String,
    val description: String,
    val level: String, // "Beginner", "Intermediate", "Advanced", "Master"
    val durationText: String,
    val instructorName: String,
    val instructorTitle: String,
    val badge: String? = null,
    val lessons: List<VideoLesson> = emptyList()
)
