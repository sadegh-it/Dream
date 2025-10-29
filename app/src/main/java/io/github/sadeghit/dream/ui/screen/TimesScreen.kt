
package io.github.sadeghit.dream.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
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
import io.github.sadeghit.dream.ui.component.DreamTopBar
import io.github.sadeghit.dream.ui.theme.Dark
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
                title = "زمان‌ها",
                navController = navController,
                isDarkTheme = isDarkTheme
            )
        }
    ) { padding ->
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
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(16.dp)
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
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = entry.meaning,  // درست
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
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