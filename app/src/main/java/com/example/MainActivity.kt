package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.CodingPracticeScreen
import com.example.ui.screens.CommunityScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.CodeUdaanTheme
import com.example.viewmodel.CodeUdaanViewModel
import com.example.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeUdaanTheme {
                CodeUdaanApp()
            }
        }
    }
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val tag: String
)

@Composable
fun CodeUdaanApp(
    viewModel: CodeUdaanViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle ViewModel snackbar notices
    LaunchedEffect(viewModel.snackbarMessage) {
        viewModel.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    // System Back Press Handler
    BackHandler(enabled = viewModel.currentScreen != Screen.WELCOME && viewModel.currentScreen != Screen.HOME) {
        if (!viewModel.navigateBack()) {
            viewModel.navigateTo(Screen.HOME)
        }
    }

    val bottomNavItems = listOf(
        BottomNavItem(Screen.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
        BottomNavItem(Screen.PRACTICE, "Practice", Icons.Filled.Code, Icons.Outlined.Code, "nav_practice"),
        BottomNavItem(Screen.COMMUNITY, "Community", Icons.Filled.Groups, Icons.Outlined.Groups, "nav_community"),
        BottomNavItem(Screen.PROFILE, "Profile", Icons.Filled.Person, Icons.Outlined.Person, "nav_profile")
    )

    val showBottomBar = viewModel.currentScreen != Screen.WELCOME

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp
                ) {
                    bottomNavItems.forEach { item ->
                        val isSelected = viewModel.currentScreen == item.screen
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.navigateTo(item.screen) },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            modifier = Modifier.testTag(item.tag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = viewModel.currentScreen,
                animationSpec = tween(durationMillis = 220),
                label = "ScreenTransition"
            ) { screen ->
                when (screen) {
                    Screen.WELCOME -> WelcomeScreen(viewModel = viewModel)
                    Screen.HOME -> HomeScreen(viewModel = viewModel)
                    Screen.PRACTICE -> CodingPracticeScreen(viewModel = viewModel)
                    Screen.COMMUNITY -> CommunityScreen(viewModel = viewModel)
                    Screen.PROFILE -> ProfileScreen(viewModel = viewModel)
                    Screen.ABOUT -> AboutScreen(viewModel = viewModel)
                }
            }
        }
    }
}
