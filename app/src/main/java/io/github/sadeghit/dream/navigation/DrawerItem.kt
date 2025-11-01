package io.github.sadeghit.dream.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector


sealed class DrawerItem(
    val title: String,
    val icon: ImageVector
) {
    object Website : DrawerItem("طراح اپلیکیشن", Icons.Default.Public)
    object Times : DrawerItem("ساعت ها", Icons.Default.AccessTime)
    object Favorites : DrawerItem("علاقه‌مندی‌ها", Icons.Default.Favorite)
    object Settings : DrawerItem("تنظیمات", Icons.Default.Settings)
    object Resources : DrawerItem("منابع", Icons.Default.AutoStories)
    object Exit : DrawerItem("خروج", Icons.AutoMirrored.Filled.ExitToApp)
}