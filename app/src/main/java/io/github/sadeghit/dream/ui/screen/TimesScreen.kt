package io.github.sadeghit.dream.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.ui.component.DreamTopBar
import io.github.sadeghit.dream.viewModel.TimeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimesScreen(
    navController: NavController,
    themeManager: ThemeManager,
    viewModel: TimeViewModel = hiltViewModel()
) {
    val times by viewModel.times.collectAsState()
    val isDarkTheme = themeManager.isDarkTheme

    Scaffold(
        topBar = {
            DreamTopBar(
                title = "ساعت ‌ها",
                navController = navController,
                isDarkTheme = isDarkTheme
            )
        }
    ) { padding ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            times.forEach { timeItem ->  // ← timeItem = { word, entries }
                // عنوان اصلی (مثل "ساعت")
                item {
                    Text(
                        text = timeItem.word,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            textDirection = TextDirection.Rtl
                        ),
                        modifier = Modifier.padding(16.dp),

                    )
                }

                // لیست ۵ تایی (هر entry)
                items(timeItem.entries) { entry ->  // ← entry = { title, meaning }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = entry.title,  // درست
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    textDirection = TextDirection.Rtl
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = entry.meaning,  // درست
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textDirection = TextDirection.Rtl
                                )
                            )

                        }
                    }
                }

                // فاصله بین بخش‌ها
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}
}