package io.github.sadeghit.dream


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.navigation.SetupNavigation
import io.github.sadeghit.dream.ui.theme.DreamTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeManager: ThemeManager by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DreamTheme(darkTheme = themeManager.isDarkTheme) {
                SetupNavigation(
                    themeManager = themeManager,

                    )
            }
        }
    }
}


