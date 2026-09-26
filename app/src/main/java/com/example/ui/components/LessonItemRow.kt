package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.LessonProgressEntity
import com.example.data.model.VideoLesson

@Composable
fun LessonItemRow(
    lesson: VideoLesson,
    progress: LessonProgressEntity?,
    isCurrentPlaying: Boolean,
    onClick: () -> Unit,
    onToggleCompleted: () -> Unit,
    onToggleBookmark: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCompleted = progress?.isCompleted == true
    val isBookmarked = progress?.isBookmarked == true
    val hasNotes = !progress?.userNotes.isNullOrBlank()

    val cardBg = when {
        isCurrentPlaying -> Color(0xFF1E293B)
        isCompleted -> Color(0xFF131D24)
        else -> Color(0xFF111827)
    }

    val borderColor = when {
        isCurrentPlaying -> MaterialTheme.colorScheme.primary
        else -> Color.Transparent
    }

    Box(
        modifier = modifier
            .testTag("lesson_item_${lesson.id}")
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(cardBg)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Play status or Lesson number
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (isCompleted) Color(0xFF10B981).copy(alpha = 0.2f)
                        else if (isCurrentPlaying) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        else Color(0xFF1F2937)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completed",
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(20.dp)
                    )
                } else if (isCurrentPlaying) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Now Playing",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = "${lesson.lessonNumber}",
                        color = Color(0xFFD1D5DB),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Lesson Title & Meta
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = lesson.title,
                    color = if (isCurrentPlaying) MaterialTheme.colorScheme.primary else Color.White,
                    fontSize = 14.sp,
                    fontWeight = if (isCurrentPlaying) FontWeight.Bold else FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(3.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = lesson.durationText,
                        color = Color(0xFF9CA3AF),
                        fontSize = 12.sp
                    )

                    if (lesson.downloadableAssets.isNotEmpty()) {
                        Text(
                            text = "• ${lesson.downloadableAssets.size} Assets",
                            color = Color(0xFF06B6D4),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    if (hasNotes) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = "Has Notes",
                                tint = Color(0xFFFBBF24),
                                modifier = Modifier.size(11.dp)
                            )
                            Text(
                                text = " Notes",
                                color = Color(0xFFFBBF24),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // Bookmark Button
            IconButton(
                onClick = onToggleBookmark,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = if (isBookmarked) "Remove Bookmark" else "Bookmark Lesson",
                    tint = if (isBookmarked) Color(0xFFFBBF24) else Color(0xFF6B7280),
                    modifier = Modifier.size(20.dp)
                )
            }

            // Toggle Complete Button
            IconButton(
                onClick = onToggleCompleted,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Outlined.CheckCircleOutline,
                    contentDescription = if (isCompleted) "Mark Incomplete" else "Mark Complete",
                    tint = if (isCompleted) Color(0xFF10B981) else Color(0xFF6B7280),
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
