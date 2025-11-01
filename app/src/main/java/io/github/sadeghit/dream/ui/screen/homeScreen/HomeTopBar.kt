package io.github.sadeghit.dream.ui.screen.homeScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.ui.component.DreamTopBar

@Composable
fun HomeTopBar(
    isDarkTheme: Boolean,
    themeManager: ThemeManager,
    onMenuClick: () -> Unit
) {
    DreamTopBar(
        title = "تعبیر خواب آبی",
        navController = rememberNavController(), // یا حذف کن
        isDarkTheme = isDarkTheme,
        navigationIcon = Icons.Default.Menu,
        onNavigationClick = onMenuClick,
        actionIcon = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
        actionContentDescription = if (isDarkTheme) "تم روشن" else "تم تاریک",
        onActionClick = { themeManager.toggleTheme() }
    )
}