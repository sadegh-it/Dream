package io.github.sadeghit.dream.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.data.model.TimeItem
import io.github.sadeghit.dream.ui.component.DreamTopBar

@Composable
fun TimeDetailScreen(
    navController: NavController,
    timeItem: TimeItem,
    themeManager: ThemeManager
) {
    val isDarkTheme = themeManager.isDarkTheme

    Scaffold(
        topBar = {
            DreamTopBar(
                title = "ساعت ها",
                navController = navController,
                isDarkTheme = isDarkTheme
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
            items(timeItem.entries) { entry ->
                Text(
                    text = entry.title,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDirection = TextDirection.Rtl
                    ),
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 8.dp)
                )

                Text(
                    text = entry.meaning,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        textDirection = TextDirection.Rtl
                    ),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 12.dp, end = 16.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }
        }
    }
}