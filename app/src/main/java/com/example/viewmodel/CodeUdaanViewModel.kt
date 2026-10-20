package com.example.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.data.Course
import com.example.data.PracticeQuestion

enum class Screen {
    WELCOME,
    HOME,
    PRACTICE,
    COMMUNITY,
    PROFILE,
    ABOUT
}

class CodeUdaanViewModel : ViewModel() {

    // Current Navigation Screen (Default WELCOME on launch)
    var currentScreen by mutableStateOf(Screen.WELCOME)
        private set

    // Previous Screen history for Back Navigation
    private val backStack = mutableListOf<Screen>()

    // In-memory User Name state
    var userName by mutableStateOf("")
        private set

    // Welcome Screen Name Input state
    var welcomeInputName by mutableStateOf("")

    // Validation message for Welcome screen
    var nameValidationError by mutableStateOf<String?>(null)

    // Bio / Description in Profile (in-memory)
    var userBio by mutableStateOf("Enthusiastic tech learner starting journey in C Programming and Web Development.")

    // Selected Course for detail dialog/view
    var selectedCourse by mutableStateOf<Course?>(null)

    // Selected Practice Question for detail modal
    var selectedQuestion by mutableStateOf<PracticeQuestion?>(null)

    // Active Category Filter for Practice Questions
    var practiceFilter by mutableStateOf("All")

    // Interactive Demo Toast/Snackbar Message State
    var snackbarMessage by mutableStateOf<String?>(null)

    fun onStartExploringClicked() {
        val trimmed = welcomeInputName.trim()
        if (trimmed.isEmpty()) {
            nameValidationError = "Please enter your name to start exploring CodeUdaan!"
        } else {
            nameValidationError = null
            userName = trimmed
            navigateTo(Screen.HOME)
        }
    }

    fun updateUserNameInProfile(newName: String) {
        val trimmed = newName.trim()
        if (trimmed.isNotEmpty()) {
            userName = trimmed
            welcomeInputName = trimmed
            showSnackbar("Name updated to '$trimmed' for current session!")
        }
    }

    fun navigateTo(screen: Screen) {
        if (currentScreen != screen) {
            backStack.add(currentScreen)
            currentScreen = screen
        }
    }

    fun navigateBack(): Boolean {
        if (backStack.isNotEmpty()) {
            currentScreen = backStack.removeAt(backStack.size - 1)
            return true
        }
        return false
    }

    fun resetToWelcomeScreen() {
        userName = ""
        welcomeInputName = ""
        nameValidationError = null
        currentScreen = Screen.WELCOME
        backStack.clear()
    }

    fun showSnackbar(message: String) {
        snackbarMessage = message
    }

    fun clearSnackbar() {
        snackbarMessage = null
    }
}
