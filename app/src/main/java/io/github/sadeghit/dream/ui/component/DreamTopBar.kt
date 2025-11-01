package io.github.sadeghit.dream.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import io.github.sadeghit.dream.navigation.Screens
import io.github.sadeghit.dream.ui.theme.AppBarBlue
import io.github.sadeghit.dream.ui.theme.Dark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DreamTopBar(
    title: String,
    navController: NavController,
    isDarkTheme: Boolean,
    // ایکون سمت چپ
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: () -> Unit = { navController.popBackStack(Screens.Home.route, inclusive = false) },
    // ایکون سمت راست
    actionIcon: ImageVector? = null,
    actionContentDescription: String? = null,
    onActionClick: (() -> Unit)? = null,
    containerColor: Color = if (isDarkTheme) Dark else AppBarBlue,
    contentColor: Color = Color.White
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = contentColor
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = if (navigationIcon == Icons.Default.Menu) "منو" else "بازگشت",
                    tint = contentColor
                )
            }
        },
        actions = {
            actionIcon?.let { icon ->
                IconButton(onClick = { onActionClick?.invoke() }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = actionContentDescription,
                        tint = contentColor
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = contentColor
        )
    )
}