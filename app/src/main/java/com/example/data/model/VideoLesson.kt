package com.example.data.model

data class DownloadableAsset(
    val title: String,
    val fileType: String, // e.g. "PSD Project", "LUT Pack", "Sound FX", "Preset"
    val size: String,
    val downloadUrl: String
)

data class VideoLesson(
    val id: String,
    val classId: String,
    val title: String,
    val lessonNumber: Int,
    val durationText: String,
    val youtubeVideoId: String, // YouTube video ID (unlisted or direct)
    val description: String,
    val keyPoints: List<String> = emptyList(),
    val downloadableAssets: List<DownloadableAsset> = emptyList()
)
