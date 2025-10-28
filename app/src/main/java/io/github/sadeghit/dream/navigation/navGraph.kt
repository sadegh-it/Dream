package io.github.sadeghit.dream.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.ui.screen.DreamDetailScreen
import io.github.sadeghit.dream.ui.screen.HomeScreen
import io.github.sadeghit.dream.ui.screen.SplashScreen
import io.github.sadeghit.dream.viewModel.DreamViewModel

@Composable
fun SetupNavigation(themeManager: ThemeManager) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }


        composable(route = Screens.Home.route) {
            HomeScreen(navController = navController, themeManager = themeManager)
        }

        composable(
            route = Screens.DreamDetail.route,
            arguments = listOf(navArgument("word") { type = NavType.StringType })
        ) { backStackEntry ->
            val wordText = backStackEntry.arguments?.getString("word") ?: ""
            val viewModel: DreamViewModel = hiltViewModel()
            val dreams by viewModel.dream.collectAsState()

            val word = dreams
                .flatMap { it.words.orEmpty() }
                .firstOrNull { it.word == wordText }

            if (word != null) {
                DreamDetailScreen(
                    navController = navController,
                    word = word,
                    themeManager = themeManager
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "کلمه یافت نشد ❌")
                }
            }
        }
    }
}
