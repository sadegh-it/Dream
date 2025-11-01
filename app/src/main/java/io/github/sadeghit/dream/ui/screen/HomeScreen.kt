package io.github.sadeghit.dream.ui.screen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.navigation.DrawerItem
import io.github.sadeghit.dream.navigation.Screens
import io.github.sadeghit.dream.ui.component.Drawer
import io.github.sadeghit.dream.ui.component.DreamTopBar
import io.github.sadeghit.dream.ui.component.LetterList
import io.github.sadeghit.dream.ui.component.SearchBar
import io.github.sadeghit.dream.ui.component.WordList
import io.github.sadeghit.dream.util.normalizeText
import io.github.sadeghit.dream.viewModel.DreamViewModel
import kotlinx.coroutines.launch

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

    val backStackEntry = navController.currentBackStackEntry
    val savedLetter = backStackEntry?.savedStateHandle?.get<String>("selected_letter")

    var selectedLetter by remember(savedLetter, dreams) {
        mutableStateOf(
            savedLetter ?: if (dreams.isNotEmpty()) dreams.first().letter else ""
        )
    }

    LaunchedEffect(dreams) {
        if (dreams.isNotEmpty() && selectedLetter.isEmpty()) {
            selectedLetter = dreams.first().letter
        }
    }

    var searchQuery by remember { mutableStateOf("") }

    val isDarkTheme = themeManager.isDarkTheme


    val filteredWords = if (searchQuery.isBlank()) {
        dreams.firstOrNull { it.letter == selectedLetter }?.words.orEmpty()
    } else {
        val q = normalizeText(searchQuery)
        dreams
            .flatMap { it.words.orEmpty() }
            .filter { normalizeText(it.word ?: "").contains(q, ignoreCase = true) }
    }
    var selectedDrawerItem by remember { mutableStateOf<DrawerItem>(DrawerItem.Times) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(
                currentItem = selectedDrawerItem,
                onItemClick = { item ->
                    scope.launch { drawerState.close() }
                    when (item) {
                        DrawerItem.Website -> {
                            val intent =
                                Intent(Intent.ACTION_VIEW, "https://sadegh-it.github.io".toUri())
                            context.startActivity(intent)
                        }

                        DrawerItem.Times -> {
                            navController.navigate(Screens.Times.route) {
                                popUpTo(Screens.Home.route) { inclusive = false }
                                launchSingleTop = true  // duplicate جلوگیری
                            }
                        }

                        DrawerItem.Favorites -> {
                            navController.navigate(Screens.Favorites.route) {
                                popUpTo(Screens.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        }

                        DrawerItem.Settings -> { /* TODO: SettingsScreen */
                        }

                        DrawerItem.Resources -> {
                            val intent =
                                Intent(Intent.ACTION_VIEW, "https://setare.com/c/17/".toUri())
                            context.startActivity(intent)
                        }

                        DrawerItem.Exit -> (context as Activity).finish()
                    }
                    selectedDrawerItem = item
                }
            )
        }
    )
    {
        Scaffold(
            topBar = {
                DreamTopBar(
                    title = "تعبیر خواب آبی",
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    navigationIcon = Icons.Default.Menu,
                    onNavigationClick = { scope.launch { drawerState.open() } },
                    actionIcon = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    actionContentDescription = if (isDarkTheme) "تم روشن" else "تم تاریک",
                    onActionClick = { themeManager.toggleTheme() }
                )
            }
        ) { padding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { focusManager.clearFocus() }
            ) {
                // ستون کلمات
                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxHeight()
                        .padding(8.dp)
                ) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it })


                    WordList(
                        words = filteredWords,
                        onWordClick = { word ->
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "selected_letter",
                                selectedLetter
                            )
                            navController.navigate(
                                Screens.DreamDetail.createRoute(
                                    word.word ?: ""
                                )
                            ) {
                                popUpTo(Screens.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.weight(0.7f)
                    )
                }

                // ستون حروف
                LetterList(
                    letters = letters,
                    selectedLetter = selectedLetter,
                    onLetterClick = {
                        focusManager.clearFocus()
                        selectedLetter = it
                        searchQuery = ""
                    },
                    modifier = Modifier.weight(0.3f)
                )
            }
        }
    }
}
