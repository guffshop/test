package com.example.data.model

enum class CategoryType(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconName: String
) {
    PHOTOSHOP(
        id = "photoshop",
        title = "Photoshop class",
        subtitle = "Photo editing, retouching & manipulations",
        iconName = "brush"
    ),
    MOBILE_VIDEOGRAPHY(
        id = "videography",
        title = "Mobile videography",
        subtitle = "Cinematic camera techniques on phone",
        iconName = "videocam"
    ),
    CAPCUT(
        id = "capcut",
        title = "Capcut editing",
        subtitle = "Viral reels, speed ramps & sound design",
        iconName = "movie"
    ),
    CONTENT_CREATION(
        id = "content_creation",
        title = "Content creation",
        subtitle = "Scripting, hooks, algorithms & branding",
        iconName = "campaign"
    ),
    SUPPORT(
        id = "support",
        title = "Support",
        subtitle = "1-on-1 mentorship, Q&A tickets & community",
        iconName = "help"
    );

    companion object {
        fun fromId(id: String): CategoryType {
            return entries.find { it.id.equals(id, ignoreCase = true) } ?: PHOTOSHOP
        }
    }
}
