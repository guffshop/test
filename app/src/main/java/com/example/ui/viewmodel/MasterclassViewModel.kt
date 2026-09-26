package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.AuthResult
import com.example.data.local.entity.LessonProgressEntity
import com.example.data.local.entity.StudentProfileEntity
import com.example.data.local.entity.SupportTicketEntity
import com.example.data.model.CategoryType
import com.example.data.model.CourseClass
import com.example.data.model.VideoLesson
import com.example.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    object Login : Screen()
    object Home : Screen()
    data class CategoryView(val category: CategoryType) : Screen()
    data class ClassDetail(val classId: String) : Screen()
    data class VideoPlayer(val classId: String, val lessonId: String) : Screen()
    object Support : Screen()
    object Profile : Screen()
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Error(val message: String) : LoginUiState()
    data class Success(val student: StudentProfileEntity) : LoginUiState()
}

class MasterclassViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CourseRepository(application)

    // Current authenticated student from Room
    val currentStudent: StateFlow<StudentProfileEntity?> = repository.currentStudentFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Progress map
    val progressMap: StateFlow<Map<String, LessonProgressEntity>> = repository.allProgressFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    // Support tickets
    val supportTickets: StateFlow<List<SupportTicketEntity>> = repository.supportTicketsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Navigation backstack
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val screenBackStack = mutableListOf<Screen>()

    // Login UI State
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    // Filter and search
    private val _selectedCategory = MutableStateFlow<CategoryType?>(null)
    val selectedCategory: StateFlow<CategoryType?> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Active playback
    private val _activeClass = MutableStateFlow<CourseClass?>(null)
    val activeClass: StateFlow<CourseClass?> = _activeClass.asStateFlow()

    private val _activeLesson = MutableStateFlow<VideoLesson?>(null)
    val activeLesson: StateFlow<VideoLesson?> = _activeLesson.asStateFlow()

    // Sheet URL config
    private val _sheetUrl = MutableStateFlow(repository.getActiveSheetUrl())
    val sheetUrl: StateFlow<String> = _sheetUrl.asStateFlow()

    init {
        // Check if student is already logged in from Room
        viewModelScope.launch {
            repository.currentStudentFlow.collect { student ->
                if (student == null) {
                    _currentScreen.value = Screen.Login
                } else if (_currentScreen.value == Screen.Login) {
                    _currentScreen.value = Screen.Home
                }
            }
        }
    }

    fun getAllClasses(): List<CourseClass> = repository.getAllClasses()

    fun getClassById(classId: String): CourseClass? = repository.getClassById(classId)

    fun getLessonById(classId: String, lessonId: String): VideoLesson? = repository.getLessonById(classId, lessonId)

    fun getFilteredClasses(): List<CourseClass> {
        val cat = _selectedCategory.value
        val query = _searchQuery.value.trim().lowercase()

        val list = if (cat != null) {
            repository.getClassesForCategory(cat)
        } else {
            repository.getAllClasses()
        }

        if (query.isEmpty()) return list

        return list.filter { course ->
            course.title.lowercase().contains(query) ||
            course.subtitle.lowercase().contains(query) ||
            course.instructorName.lowercase().contains(query) ||
            course.lessons.any { it.title.lowercase().contains(query) }
        }
    }

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenBackStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenBackStack.isNotEmpty()) {
            val previous = screenBackStack.removeAt(screenBackStack.lastIndex)
            _currentScreen.value = previous
            return true
        }
        if (_currentScreen.value !is Screen.Home && _currentScreen.value !is Screen.Login) {
            _currentScreen.value = Screen.Home
            return true
        }
        return false
    }

    fun selectCategory(category: CategoryType?) {
        _selectedCategory.value = category
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun openCategoryScreen(category: CategoryType) {
        _selectedCategory.value = category
        navigateTo(Screen.CategoryView(category))
    }

    fun openClass(classId: String) {
        val course = repository.getClassById(classId)
        _activeClass.value = course
        navigateTo(Screen.ClassDetail(classId))
    }

    fun openLesson(classId: String, lessonId: String) {
        val course = repository.getClassById(classId)
        val lesson = repository.getLessonById(classId, lessonId)
        _activeClass.value = course
        _activeLesson.value = lesson
        navigateTo(Screen.VideoPlayer(classId, lessonId))
    }

    fun playNextLesson() {
        val currentLesson = _activeLesson.value ?: return
        val currentCourse = _activeClass.value ?: return
        val nextIdx = currentCourse.lessons.indexOfFirst { it.id == currentLesson.id } + 1
        if (nextIdx in currentCourse.lessons.indices) {
            val nextLesson = currentCourse.lessons[nextIdx]
            _activeLesson.value = nextLesson
            _currentScreen.value = Screen.VideoPlayer(currentCourse.id, nextLesson.id)
        }
    }

    fun playPrevLesson() {
        val currentLesson = _activeLesson.value ?: return
        val currentCourse = _activeClass.value ?: return
        val prevIdx = currentCourse.lessons.indexOfFirst { it.id == currentLesson.id } - 1
        if (prevIdx in currentCourse.lessons.indices) {
            val prevLesson = currentCourse.lessons[prevIdx]
            _activeLesson.value = prevLesson
            _currentScreen.value = Screen.VideoPlayer(currentCourse.id, prevLesson.id)
        }
    }

    fun login(email: String, passcode: String) {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading
            when (val result = repository.verifyAndLoginStudent(email, passcode)) {
                is AuthResult.Success -> {
                    val entity = StudentProfileEntity(
                        email = result.student.email,
                        name = result.student.name,
                        status = result.student.status,
                        tier = result.student.tier,
                        sheetSource = result.student.sheetSource
                    )
                    _loginUiState.value = LoginUiState.Success(entity)
                    _currentScreen.value = Screen.Home
                }
                is AuthResult.Error -> {
                    _loginUiState.value = LoginUiState.Error(result.message)
                }
            }
        }
    }

    fun clearLoginError() {
        _loginUiState.value = LoginUiState.Idle
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _activeClass.value = null
            _activeLesson.value = null
            screenBackStack.clear()
            _currentScreen.value = Screen.Login
            _loginUiState.value = LoginUiState.Idle
        }
    }

    fun toggleLessonCompleted(lessonId: String, classId: String) {
        viewModelScope.launch {
            val isCurrentlyCompleted = progressMap.value[lessonId]?.isCompleted == true
            repository.toggleLessonCompleted(lessonId, classId, isCurrentlyCompleted)
        }
    }

    fun toggleBookmark(lessonId: String, classId: String) {
        viewModelScope.launch {
            val isCurrentlyBookmarked = progressMap.value[lessonId]?.isBookmarked == true
            repository.toggleBookmark(lessonId, classId, isCurrentlyBookmarked)
        }
    }

    fun saveLessonNotes(lessonId: String, classId: String, notes: String) {
        viewModelScope.launch {
            repository.saveLessonNotes(lessonId, classId, notes)
        }
    }

    fun submitSupportTicket(subject: String, category: String, message: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val student = currentStudent.value
            val email = student?.email ?: "student@masterclass.com"
            val name = student?.name ?: "Creative Student"
            repository.submitSupportTicket(email, name, category, subject, message)
            onSuccess()
        }
    }

    fun updateSheetUrl(url: String) {
        repository.setCustomSheetUrl(url)
        _sheetUrl.value = repository.getActiveSheetUrl()
    }
}
