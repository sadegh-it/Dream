package io.github.sadeghit.dream.navigation



sealed class Screens(val route: String) {
    data object Home : Screens("home")
    data object Favorites : Screens("favorites")
    data object SplashScreen : Screens("splashscreen")
    data object DreamDetail : Screens("dream_detail/{word}") {
        fun createRoute(word: String) = "dream_detail/$word"
    }
}
