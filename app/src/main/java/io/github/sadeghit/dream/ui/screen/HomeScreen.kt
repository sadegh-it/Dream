package io.github.sadeghit.dream.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.sadeghit.dream.data.dataStore.ThemeManager
import io.github.sadeghit.dream.navigation.Screens
import io.github.sadeghit.dream.ui.theme.AppBarBlue
import io.github.sadeghit.dream.ui.theme.Dark
import io.github.sadeghit.dream.viewModel.DreamViewModel
import kotlinx.coroutines.launch

// 🔹 تابع نرمال‌سازی برای حروف فارسی
fun normalizeText(text: String): String {
    return text
        .replace("ي", "ی")
        .replace("ك", "ک")
        .trim()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    themeManager: ThemeManager,
    viewModel: DreamViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val isDarkTheme = themeManager.isDarkTheme

    val dreams by viewModel.dream.collectAsState()
    val letters = dreams.map { it.letter }.distinct()

    var selectedLetter by remember { mutableStateOf("الف") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredWords = remember(searchQuery, selectedLetter, dreams) {
        val normalizedQuery = normalizeText(searchQuery)
        if (normalizedQuery.isBlank()) {
            dreams.firstOrNull { it.letter == selectedLetter }?.words ?: emptyList()
        } else {
            dreams.flatMap { it.words.orEmpty() }
                .filter {
                    normalizeText(it.word ?: "").contains(
                        normalizedQuery,
                        ignoreCase = true
                    )
                }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxHeight()
                    .width(250.dp)
                    .padding(16.dp)
            ) {
                Text(text = "Drawer خالیه")
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("تعبیر خواب آبی", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { themeManager.toggleTheme() }) {
                            Icon(
                                imageVector = if (themeManager.isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = "تغییر تم",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = if (themeManager.isDarkTheme) Dark else AppBarBlue,
                        titleContentColor = Color.White
                    )
                )

            }

        ) { padding ->

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        focusManager.clearFocus()
                    }
            ) {

                // 🔹 ستون کلمات و سرچ (سمت راست)
                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxHeight()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = {
                            Text(
                                "جستجو...",
                                style = TextStyle(textDirection = TextDirection.Rtl),
                                modifier = Modifier.fillMaxWidth() // حتماً اضافه کن تا راست‌چین شود
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.Right,
                            textDirection = TextDirection.Rtl // متن تایپ‌شده هم راست‌چین شود
                        ),
                        trailingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )


                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.fillMaxSize()
                    ) {

                        if (filteredWords.isEmpty()) {
                            item {
                                Text(
                                    text = "کلمه‌ای یافت نشد ❌",
                                    color = Color.Gray,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        } else {
                            items(filteredWords) { word ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.navigate(
                                                Screens.DreamDetail.createRoute(word.word ?: "")
                                            )
                                        }
                                        .padding(vertical = 6.dp)
                                ) {
                                    Text(
                                        text = word.word ?: "",
                                        color = if (isDarkTheme) Color.White else Color.Black // ← تغییر
                                    )
                                    HorizontalDivider(
                                        modifier = Modifier.padding(top = 8.dp),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                    )
                                }
                            }

                        }
                    }
                }

                // 🔹 ستون حروف الفبا (سمت چپ)
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxSize()
                        .padding(top = 16.dp, end = 8.dp)
                ) {
                    items(letters) { letter ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    focusManager.clearFocus()
                                    selectedLetter = letter
                                    searchQuery = "" // ریست سرچ
                                }
                                .background(
                                    if (selectedLetter == letter)
                                        if (isDarkTheme) Color(0xFF3C3C3C) else Color(0xFFE0E0E0)
                                    else Color.Transparent
                                )
                        ) {
                            Text(
                                text = letter,
                                color = if (selectedLetter == letter) {
                                    if (isDarkTheme) Color.White else Color.Black
                                } else {
                                    if (isDarkTheme) Color.LightGray else Color.Black
                                },
                                modifier = Modifier.padding(8.dp)
                            )
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                            )
                        }

                    }
                }
            }
        }
    }
}
