package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MasterclassPrimaryLight,
    onPrimary = Color.White,
    primaryContainer = MasterclassPrimaryDark,
    onPrimaryContainer = Color.White,
    secondary = MasterclassSecondary,
    onSecondary = Color.Black,
    tertiary = MasterclassTertiary,
    background = MasterclassDarkBackground,
    onBackground = TextPrimary,
    surface = MasterclassDarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = MasterclassDarkSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = MasterclassDarkBorder,
    error = ErrorRed,
    onError = Color.White
)

private val LightColorScheme = darkColorScheme(
    primary = MasterclassPrimary,
    onPrimary = Color.White,
    primaryContainer = MasterclassPrimaryDark,
    onPrimaryContainer = Color.White,
    secondary = MasterclassSecondary,
    onSecondary = Color.Black,
    tertiary = MasterclassTertiary,
    background = MasterclassDarkBackground,
    onBackground = TextPrimary,
    surface = MasterclassDarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = MasterclassDarkSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = MasterclassDarkBorder,
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to sleek masterclass dark theme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
