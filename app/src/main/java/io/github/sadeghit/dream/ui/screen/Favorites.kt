package io.github.sadeghit.dream.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.navigation.Screens
import io.github.sadeghit.dream.viewModel.FavoritesViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favorites(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    themeManager: ThemeManager,
) {
    val favorites = favoritesViewModel.favorites.value
    val typography = MaterialTheme.typography
    val colorScheme = MaterialTheme.colorScheme
    val isDarkTheme = themeManager.isDarkTheme

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("علاقه‌مندی‌ها") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screens.Home.route) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "بازگشت",
                            tint = colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isDarkTheme) colorScheme.surfaceVariant else colorScheme.primary,
                    titleContentColor = if (isDarkTheme) colorScheme.onSurfaceVariant else colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "هنوز هیچ علاقه‌مندی‌ای اضافه نکردی 💭",
                    style = typography.bodyLarge.copy(color = colorScheme.onSurface.copy(alpha = 0.6f))
                )
            }
        } else {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(favorites.toList()) { word ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screens.DreamDetail.createRoute(word)
                                )
                            }
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = word,
                            style = typography.bodyLarge.copy(color = colorScheme.onBackground)
                        )
                        Divider(
                            modifier = Modifier.padding(top = 8.dp),
                            thickness = 1.dp,
                            color = colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }
    }
}