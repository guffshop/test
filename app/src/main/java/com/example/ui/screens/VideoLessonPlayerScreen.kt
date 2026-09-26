package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CourseClass
import com.example.data.model.DownloadableAsset
import com.example.data.model.VideoLesson
import com.example.ui.components.YouTubePlayerView
import com.example.ui.viewmodel.MasterclassViewModel

@Composable
fun VideoLessonPlayerScreen(
    classId: String,
    lessonId: String,
    viewModel: MasterclassViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val courseClass = viewModel.activeClass.collectAsState().value ?: viewModel.getClassById(classId)
    val activeLesson = viewModel.activeLesson.collectAsState().value ?: courseClass?.lessons?.find { it.id == lessonId }
    val progressMap by viewModel.progressMap.collectAsState()

    if (courseClass == null || activeLesson == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF090D16)),
            contentAlignment = Alignment.Center
        ) {
            Text("Lesson not found", color = Color.White)
        }
        return
    }

    val progress = progressMap[activeLesson.id]
    val isCompleted = progress?.isCompleted == true
    val isBookmarked = progress?.isBookmarked == true

    var selectedTab by remember { mutableIntStateOf(0) }
    var notesInput by remember(activeLesson.id, progress?.userNotes) {
        mutableStateOf(progress?.userNotes ?: "")
    }

    val currentIndex = courseClass.lessons.indexOfFirst { it.id == activeLesson.id }
    val hasPrev = currentIndex > 0
    val hasNext = currentIndex in 0 until (courseClass.lessons.size - 1)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF090D16))
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0D1424))
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateBack() },
                modifier = Modifier.testTag("video_player_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = courseClass.title,
                    color = Color(0xFF9CA3AF),
                    fontSize = 11.sp,
                    maxLines = 1
                )
                Text(
                    text = activeLesson.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }

            IconButton(
                onClick = { viewModel.toggleBookmark(activeLesson.id, courseClass.id) }
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = if (isBookmarked) Color(0xFFFBBF24) else Color(0xFF9CA3AF)
                )
            }

            IconButton(
                onClick = { viewModel.toggleLessonCompleted(activeLesson.id, courseClass.id) }
            ) {
                Icon(
                    imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Outlined.CheckCircleOutline,
                    contentDescription = "Mark Complete",
                    tint = if (isCompleted) Color(0xFF10B981) else Color(0xFF9CA3AF)
                )
            }
        }

        // Embedded YouTube Player
        YouTubePlayerView(
            youtubeVideoId = activeLesson.youtubeVideoId,
            modifier = Modifier.fillMaxWidth()
        )

        // Lesson controls and Tabs
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Lesson Title & Completion Action Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF111827)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "LESSON ${activeLesson.lessonNumber} OF ${courseClass.lessons.size}",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = activeLesson.durationText,
                                color = Color(0xFF9CA3AF),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = activeLesson.title,
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Toggle completion button
                        Button(
                            onClick = {
                                viewModel.toggleLessonCompleted(activeLesson.id, courseClass.id)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCompleted) Color(0xFF064E3B) else Color(0xFF1F2937)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .testTag("toggle_complete_button")
                                .fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Outlined.CheckCircleOutline,
                                contentDescription = null,
                                tint = if (isCompleted) Color(0xFF34D399) else Color(0xFF9CA3AF),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isCompleted) "Completed! (Click to redo)" else "Mark Lesson as Completed",
                                color = if (isCompleted) Color(0xFF34D399) else Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Tab bar: 0=Overview, 1=Assets, 2=My Notes
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color(0xFF111827),
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clip(RoundedCornerShape(12.dp))
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Overview", fontSize = 13.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Text(
                                "Assets (${activeLesson.downloadableAssets.size})",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("My Notes", fontSize = 13.sp, fontWeight = FontWeight.Bold) }
                    )
                }
            }

            // Tab Content
            when (selectedTab) {
                0 -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF111827))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Lesson Summary",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = activeLesson.description,
                                color = Color(0xFFD1D5DB),
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )

                            if (activeLesson.keyPoints.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = "Key Takeaways",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                activeLesson.keyPoints.forEach { point ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 3.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(text = "• ", color = MaterialTheme.colorScheme.primary, fontSize = 15.sp)
                                        Text(
                                            text = point,
                                            color = Color(0xFFE5E7EB),
                                            fontSize = 13.sp,
                                            lineHeight = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> {
                    if (activeLesson.downloadableAssets.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFF111827))
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No downloadable assets required for this lesson.",
                                    color = Color(0xFF9CA3AF),
                                    fontSize = 13.sp
                                )
                            }
                        }
                    } else {
                        items(activeLesson.downloadableAssets) { asset ->
                            AssetDownloadCard(asset = asset)
                        }
                    }
                }

                2 -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF111827))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Student Study Notes (Saved locally in Room)",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Record your timestamps, shortcuts, and key project ideas",
                                color = Color(0xFF9CA3AF),
                                fontSize = 11.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = notesInput,
                                onValueChange = { notesInput = it },
                                placeholder = {
                                    Text(
                                        "Write personal study notes here (e.g., 04:12 - remembers to invert layer mask)",
                                        color = Color(0xFF6B7280)
                                    )
                                },
                                minLines = 4,
                                maxLines = 8,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = Color(0xFF374151),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .testTag("lesson_notes_input")
                                    .fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = {
                                    viewModel.saveLessonNotes(activeLesson.id, courseClass.id, notesInput)
                                    Toast.makeText(context, "Notes saved successfully!", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .testTag("save_notes_button")
                                    .align(Alignment.End)
                            ) {
                                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Save Notes")
                            }
                        }
                    }
                }
            }
        }

        // Bottom Prev / Next Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0D1424))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { viewModel.playPrevLesson() },
                enabled = hasPrev,
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Previous")
            }

            Text(
                text = "${currentIndex + 1} / ${courseClass.lessons.size}",
                color = Color(0xFF9CA3AF),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { viewModel.playNextLesson() },
                enabled = hasNext,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Next Lesson")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun AssetDownloadCard(asset: DownloadableAsset) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2333)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(asset.downloadUrl))
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Asset link: ${asset.title}", Toast.LENGTH_SHORT).show()
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF0284C7).copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FileDownload,
                        contentDescription = null,
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Column {
                    Text(
                        text = asset.title,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${asset.fileType} • ${asset.size}",
                        color = Color(0xFF9CA3AF),
                        fontSize = 11.sp
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = "Download",
                tint = Color(0xFF38BDF8),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
