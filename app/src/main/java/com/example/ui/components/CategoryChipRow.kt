package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CategoryType
import com.example.ui.theme.CategoryCapcut
import com.example.ui.theme.CategoryContent
import com.example.ui.theme.CategoryPhotoshop
import com.example.ui.theme.CategorySupport
import com.example.ui.theme.CategoryVideography

@Composable
fun CategoryChipRow(
    selectedCategory: CategoryType?,
    onCategorySelected: (CategoryType?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            val isSelected = selectedCategory == null
            CategoryChipItem(
                title = "All Classes",
                icon = Icons.Default.Movie,
                accentColor = MaterialTheme.colorScheme.primary,
                isSelected = isSelected,
                onClick = { onCategorySelected(null) },
                testTag = "category_chip_all"
            )
        }

        items(CategoryType.entries.toTypedArray()) { category ->
            val isSelected = selectedCategory == category
            val (icon, color) = when (category) {
                CategoryType.PHOTOSHOP -> Icons.Default.Brush to CategoryPhotoshop
                CategoryType.MOBILE_VIDEOGRAPHY -> Icons.Default.Videocam to CategoryVideography
                CategoryType.CAPCUT -> Icons.Default.Movie to CategoryCapcut
                CategoryType.CONTENT_CREATION -> Icons.Default.Campaign to CategoryContent
                CategoryType.SUPPORT -> Icons.Default.Help to CategorySupport
            }

            CategoryChipItem(
                title = category.title,
                icon = icon,
                accentColor = color,
                isSelected = isSelected,
                onClick = { onCategorySelected(category) },
                testTag = "category_chip_${category.id}"
            )
        }
    }
}

@Composable
private fun CategoryChipItem(
    title: String,
    icon: ImageVector,
    accentColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val bg = if (isSelected) accentColor else Color(0xFF1F2937)
    val contentColor = if (isSelected) Color.White else Color(0xFFD1D5DB)

    Box(
        modifier = Modifier
            .testTag(testTag)
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 9.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else accentColor,
                modifier = Modifier.size(17.dp)
            )
            Text(
                text = title,
                color = contentColor,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}
