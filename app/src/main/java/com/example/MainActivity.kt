package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CategoryType
import com.example.ui.screens.CategoryDetailScreen
import com.example.ui.screens.ClassDetailScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SupportScreen
import com.example.ui.screens.VideoLessonPlayerScreen
import com.example.ui.theme.CategoryCapcut
import com.example.ui.theme.CategoryPhotoshop
import com.example.ui.theme.CategorySupport
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.MasterclassViewModel
import com.example.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {

    private val viewModel: MasterclassViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val currentScreen by viewModel.currentScreen.collectAsState()
                val currentStudent by viewModel.currentStudent.collectAsState()

                // Intercept back button if not in Home or Login
                BackHandler(enabled = currentScreen !is Screen.Home && currentScreen !is Screen.Login) {
                    viewModel.navigateBack()
                }

                val showBottomNav = currentStudent != null && currentScreen !is Screen.Login && currentScreen !is Screen.VideoPlayer

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.safeDrawing,
                    containerColor = Color(0xFF090D16),
                    bottomBar = {
                        if (showBottomNav) {
                            MasterclassBottomNav(
                                currentScreen = currentScreen,
                                onNavigateToHome = {
                                    viewModel.selectCategory(null)
                                    viewModel.navigateTo(Screen.Home)
                                },
                                onNavigateToPhotoshop = {
                                    viewModel.openCategoryScreen(CategoryType.PHOTOSHOP)
                                },
                                onNavigateToCapCut = {
                                    viewModel.openCategoryScreen(CategoryType.CAPCUT)
                                },
                                onNavigateToSupport = {
                                    viewModel.navigateTo(Screen.Support)
                                },
                                onNavigateToProfile = {
                                    viewModel.navigateTo(Screen.Profile)
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AnimatedContent(
                            targetState = currentScreen,
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "ScreenTransition"
                        ) { screen ->
                            when (screen) {
                                is Screen.Login -> LoginScreen(viewModel = viewModel)
                                is Screen.Home -> HomeScreen(viewModel = viewModel)
                                is Screen.CategoryView -> CategoryDetailScreen(
                                    category = screen.category,
                                    viewModel = viewModel
                                )
                                is Screen.ClassDetail -> ClassDetailScreen(
                                    classId = screen.classId,
                                    viewModel = viewModel
                                )
                                is Screen.VideoPlayer -> VideoLessonPlayerScreen(
                                    classId = screen.classId,
                                    lessonId = screen.lessonId,
                                    viewModel = viewModel
                                )
                                is Screen.Support -> SupportScreen(viewModel = viewModel)
                                is Screen.Profile -> ProfileScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MasterclassBottomNav(
    currentScreen: Screen,
    onNavigateToHome: () -> Unit,
    onNavigateToPhotoshop: () -> Unit,
    onNavigateToCapCut: () -> Unit,
    onNavigateToSupport: () -> Unit,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .testTag("bottom_navigation_bar")
            .windowInsetsPadding(WindowInsets.navigationBars),
        containerColor = Color(0xFF0D1424),
        tonalElevation = 8.dp
    ) {
        // Home / All Classes
        NavigationBarItem(
            selected = currentScreen is Screen.Home,
            onClick = onNavigateToHome,
            icon = {
                Icon(Icons.Default.School, contentDescription = "Classes")
            },
            label = { Text("Classes", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                unselectedIconColor = Color(0xFF6B7280),
                unselectedTextColor = Color(0xFF6B7280)
            ),
            modifier = Modifier.testTag("nav_item_home")
        )

        // Photoshop
        NavigationBarItem(
            selected = currentScreen is Screen.CategoryView && currentScreen.category == CategoryType.PHOTOSHOP,
            onClick = onNavigateToPhotoshop,
            icon = {
                Icon(Icons.Default.Brush, contentDescription = "Photoshop")
            },
            label = { Text("Photoshop", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CategoryPhotoshop,
                selectedTextColor = CategoryPhotoshop,
                indicatorColor = CategoryPhotoshop.copy(alpha = 0.18f),
                unselectedIconColor = Color(0xFF6B7280),
                unselectedTextColor = Color(0xFF6B7280)
            ),
            modifier = Modifier.testTag("nav_item_photoshop")
        )

        // CapCut
        NavigationBarItem(
            selected = currentScreen is Screen.CategoryView && currentScreen.category == CategoryType.CAPCUT,
            onClick = onNavigateToCapCut,
            icon = {
                Icon(Icons.Default.Movie, contentDescription = "CapCut")
            },
            label = { Text("CapCut", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CategoryCapcut,
                selectedTextColor = CategoryCapcut,
                indicatorColor = CategoryCapcut.copy(alpha = 0.18f),
                unselectedIconColor = Color(0xFF6B7280),
                unselectedTextColor = Color(0xFF6B7280)
            ),
            modifier = Modifier.testTag("nav_item_capcut")
        )

        // Support
        NavigationBarItem(
            selected = currentScreen is Screen.Support,
            onClick = onNavigateToSupport,
            icon = {
                Icon(Icons.Default.Help, contentDescription = "Support")
            },
            label = { Text("Support", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CategorySupport,
                selectedTextColor = CategorySupport,
                indicatorColor = CategorySupport.copy(alpha = 0.18f),
                unselectedIconColor = Color(0xFF6B7280),
                unselectedTextColor = Color(0xFF6B7280)
            ),
            modifier = Modifier.testTag("nav_item_support")
        )

        // Profile
        NavigationBarItem(
            selected = currentScreen is Screen.Profile,
            onClick = onNavigateToProfile,
            icon = {
                Icon(Icons.Default.Person, contentDescription = "Profile")
            },
            label = { Text("Profile", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                unselectedIconColor = Color(0xFF6B7280),
                unselectedTextColor = Color(0xFF6B7280)
            ),
            modifier = Modifier.testTag("nav_item_profile")
        )
    }
}
