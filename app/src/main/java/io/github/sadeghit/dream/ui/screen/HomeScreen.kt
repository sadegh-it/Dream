package io.github.sadeghit.dream.ui.screen

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.navigation.Screens
import io.github.sadeghit.dream.ui.component.Drawer
import io.github.sadeghit.dream.ui.screen.homeScreen.HomeContent
import io.github.sadeghit.dream.ui.screen.homeScreen.HomeTopBar
import io.github.sadeghit.dream.ui.screen.homeScreen.handleDrawerNavigation
import io.github.sadeghit.dream.viewModel.DreamViewModel
import kotlinx.coroutines.launch
import rememberHomeScreenState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    themeManager: ThemeManager,
    viewModel: DreamViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val dreams by viewModel.dream.collectAsState()
    val letters = dreams.map { it.letter }

    // --- State Manager ---
    val state = rememberHomeScreenState(
        dreams = dreams,
        navController = navController
    )

    val isDarkTheme = themeManager.isDarkTheme

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(
                currentItem = state.selectedDrawerItem.value,
                onItemClick = { item ->
                    scope.launch { drawerState.close() }
                    handleDrawerNavigation(item, navController, context)
                    state.selectedDrawerItem.value = item
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    isDarkTheme = isDarkTheme,
                    themeManager = themeManager,
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }
        ) { padding ->
            HomeContent(
                padding = padding,
                state = state,
                letters = letters,
                onLetterClick = {
                    focusManager.clearFocus()
                    state.selectedLetter.value = it
                    state.searchQuery.value = ""
                },
                onWordClick = { word ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "selected_letter", state.selectedLetter.value
                    )
                    navController.navigate(Screens.DreamDetail.createRoute(word.word ?: "")) {
                        popUpTo(Screens.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onSearchChange = { state.searchQuery.value = it },
                focusManager = focusManager
            )
        }
    }
}