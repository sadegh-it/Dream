package io.github.sadeghit.dream


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.navigation.SetupNavigation
import io.github.sadeghit.dream.ui.theme.DreamTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeManager: ThemeManager = hiltViewModel()  // ✅ محلی!
            DreamTheme(darkTheme = themeManager.isDarkTheme) {
                SetupNavigation()  // بدون param
            }
        }
    }
}


