package io.github.sadeghit.dream.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.data.model.DreamWord
import io.github.sadeghit.dream.ui.component.DreamTopBar
import io.github.sadeghit.dream.viewModel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    word: DreamWord,
    themeManager: ThemeManager,
    favoritesViewModel: FavoritesViewModel
) {

    val isDarkTheme = themeManager.isDarkTheme
    var isFavorite by remember { mutableStateOf(false) }


    val favorites by favoritesViewModel.favorites
    LaunchedEffect(favorites) {
        isFavorite = word.word in favorites
    }



    Scaffold(
        topBar = {
            DreamTopBar(
                title = word.word ?: "",
                navController = navController,
                isDarkTheme = isDarkTheme,
                actionIcon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                actionContentDescription = if (isFavorite) "حذف از علاقه‌مندی" else "افزودن به علاقه‌مندی",
                onActionClick = {
                    if (isFavorite) {
                        favoritesViewModel.removeFavorite(word.word ?: "")
                    } else {
                        favoritesViewModel.addFavorite(word.word ?: "")
                    }
                    isFavorite = !isFavorite
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.End
        ) {
            items(word.authors.orEmpty()) { author ->
                // نویسنده بولد
                Text(
                    text = author.author ?: "",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDirection = TextDirection.Rtl
                    ),
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, end = 12.dp, top = 8.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // متن تعبیر
                Text(
                    text = author.meaning ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        textDirection = TextDirection.Rtl
                    ),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 4.dp),

                    )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }
        }
    }
}
