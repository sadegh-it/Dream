import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import io.github.sadeghit.dream.data.model.DreamLetter
import io.github.sadeghit.dream.navigation.DrawerItem
 import io.github.sadeghit.dream.ui.screen.homeScreen.HomeScreenStateData
import io.github.sadeghit.dream.ui.screen.homeScreen.HomeScreenStateImpl

@Composable
fun rememberHomeScreenState(
    dreams: List<DreamLetter>,
    navController: NavController
): HomeScreenStateImpl {
    val backStackEntry = navController.currentBackStackEntry
    val savedLetter = backStackEntry?.savedStateHandle?.get<String>("selected_letter")

    return remember(dreams, savedLetter) {
        val initialLetter = savedLetter ?: dreams.firstOrNull()?.letter.orEmpty()

        val data = HomeScreenStateData(
            selectedLetter = mutableStateOf(initialLetter),
            searchQuery = mutableStateOf(""),
            selectedDrawerItem = mutableStateOf(DrawerItem.Times)
        )

        HomeScreenStateImpl(data, dreams)
    }
}