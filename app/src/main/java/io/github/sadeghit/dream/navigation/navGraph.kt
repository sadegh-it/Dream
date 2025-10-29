package io.github.sadeghit.dream.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.ui.screen.DetailScreen
import io.github.sadeghit.dream.ui.screen.Favorites
import io.github.sadeghit.dream.ui.screen.HomeScreen
import io.github.sadeghit.dream.ui.screen.SplashScreen
import io.github.sadeghit.dream.ui.screen.TimesScreen
import io.github.sadeghit.dream.viewModel.DreamViewModel
import io.github.sadeghit.dream.viewModel.FavoritesViewModel


@Composable
fun SetupNavigation() {  // بدون param
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route  // ✅ از Splash!
    ) {
        composable(Screens.Times.route) {
            val themeManager: ThemeManager = hiltViewModel()
            TimesScreen(navController = navController, themeManager = themeManager)
        }
        // Splash
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController)
        }
        // Home
        composable(Screens.Home.route) {
            val themeManager: ThemeManager = hiltViewModel()  // ✅ محلی
            val dreamVM: DreamViewModel = hiltViewModel()
            HomeScreen(navController, themeManager, dreamVM)
        }
        // Favorites
        composable(Screens.Favorites.route) {
            val themeManager: ThemeManager = hiltViewModel()
            val favVM: FavoritesViewModel = hiltViewModel()
            Favorites(navController, favVM, themeManager)
        }
        // Detail (بهبود: word پیدا کردن سریع‌تر)
        composable(
            Screens.DreamDetail.route,
            arguments = listOf(navArgument("word") { type = NavType.StringType })
        ) { entry ->
            val wordText = entry.arguments?.getString("word") ?: ""
            val themeManager: ThemeManager = hiltViewModel()
            val dreamVM: DreamViewModel = hiltViewModel()
            val favVM: FavoritesViewModel = hiltViewModel()
            val dreams by dreamVM.dream.collectAsState()

            val word = dreams.flatMap { it.words.orEmpty() }
                .firstOrNull { it.word == wordText }

            if (word != null) {
                DetailScreen(navController, word, themeManager, favVM)
            } else {
                LaunchedEffect(Unit) { navController.popBackStack() }  // ✅ Auto back
                Box(Modifier.fillMaxSize()) { /* Loading... */ }
            }
        }
    }
}