package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.datasource.PredefinedClasses
import com.example.data.model.CategoryType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Creative Masterclass", appName)
  }

  @Test
  fun `verify all required categories exist in curriculum`() {
    val categories = PredefinedClasses.allClasses.map { it.category }.distinct()
    assertTrue(categories.contains(CategoryType.PHOTOSHOP))
    assertTrue(categories.contains(CategoryType.MOBILE_VIDEOGRAPHY))
    assertTrue(categories.contains(CategoryType.CAPCUT))
    assertTrue(categories.contains(CategoryType.CONTENT_CREATION))
    assertTrue(categories.contains(CategoryType.SUPPORT))
  }

  @Test
  fun `verify lessons contain unlisted youtube video ids`() {
    val classes = PredefinedClasses.allClasses
    assertTrue(classes.isNotEmpty())
    classes.forEach { courseClass ->
      assertTrue(courseClass.lessons.isNotEmpty())
      courseClass.lessons.forEach { lesson ->
        assertNotNull(lesson.youtubeVideoId)
        assertTrue(lesson.youtubeVideoId.isNotBlank())
      }
    }
  }
}
