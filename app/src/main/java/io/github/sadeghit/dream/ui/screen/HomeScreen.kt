package io.github.sadeghit.dream.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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

fun normalizeText(text: String): String =
    text.replace("ي", "ی").replace("ك", "ک").trim()

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

    val dreams by viewModel.dream.collectAsState()
    val letters = dreams.map { it.letter }.distinct()

    var selectedLetter by remember { mutableStateOf("الف") }
    var searchQuery by remember { mutableStateOf("") }

    val isDarkTheme = themeManager.isDarkTheme
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val filteredWords = remember(searchQuery, selectedLetter, dreams) {
        val normalizedQuery = normalizeText(searchQuery)
        if (normalizedQuery.isBlank()) {
            dreams.firstOrNull { it.letter == selectedLetter }?.words ?: emptyList()
        } else {
            dreams.flatMap { it.words.orEmpty() }
                .filter { normalizeText(it.word ?: "").contains(normalizedQuery, ignoreCase = true) }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .background(colorScheme.background)
                    .fillMaxHeight()
                    .width(250.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Drawer خالیه",
                    style = typography.titleMedium.copy(color = colorScheme.onBackground)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("تعبیر خواب", style = typography.titleLarge, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint =  Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = { themeManager.toggleTheme() }) {
                            Icon(
                                imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = "تغییر تم",
                                tint =  Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = if (isDarkTheme) Dark else AppBarBlue,
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
                    ) { focusManager.clearFocus() }
            ) {
                // ستون کلمات
                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxHeight()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("جستجو...", style = typography.bodyMedium, textAlign = TextAlign.Right) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        singleLine = true,
                        textStyle = typography.bodyMedium.copy(textAlign = TextAlign.Right, textDirection = TextDirection.Rtl),
                        trailingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = colorScheme.onSurface)
                        }
                    )

                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (filteredWords.isEmpty()) {
                            item {
                                Text(
                                    "کلمه‌ای یافت نشد ❌",
                                    style = typography.bodyMedium.copy(color = colorScheme.onSurface.copy(alpha = 0.6f)),
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
                                            navController.navigate(Screens.DreamDetail.createRoute(word.word ?: ""))
                                        }
                                        .padding(vertical = 6.dp)
                                ) {
                                    Text(
                                        text = word.word ?: "",
                                        style = typography.bodyLarge.copy(color = colorScheme.onBackground)
                                    )
                                    Divider(
                                        modifier = Modifier.padding(top = 8.dp),
                                        thickness = 1.dp,
                                        color = colorScheme.outline.copy(alpha = 0.3f)
                                    )
                                }
                            }
                        }
                    }
                }

                // ستون حروف
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxSize()
                        .padding(top = 16.dp, end = 8.dp)
                ) {
                    items(letters) { letter ->
                        val isSelected = selectedLetter == letter
                        val bgColor = if (isSelected) colorScheme.secondaryContainer else Color.Transparent
                        val textColor = if (isSelected) colorScheme.onSecondaryContainer else colorScheme.onBackground

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(bgColor)
                                .clickable {
                                    focusManager.clearFocus()
                                    selectedLetter = letter
                                    searchQuery = ""
                                }
                        ) {
                            Text(
                                text = letter,
                                style = typography.bodyLarge.copy(color = textColor),
                                modifier = Modifier.padding(8.dp)
                            )
                            Divider(thickness = 1.dp, color = colorScheme.outline.copy(alpha = 0.3f))
                        }
                    }
                }
            }
        }
    }
}
