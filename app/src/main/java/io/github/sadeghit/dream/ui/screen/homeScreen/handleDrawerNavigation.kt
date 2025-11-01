package io.github.sadeghit.dream.ui.screen.homeScreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.navigation.NavController
import io.github.sadeghit.dream.navigation.DrawerItem
import io.github.sadeghit.dream.navigation.Screens


fun handleDrawerNavigation(
    item: DrawerItem,
    navController: NavController,
    context: Context
) {
    when (item) {
        DrawerItem.Website -> {
            val intent = Intent(Intent.ACTION_VIEW, "https://github.com/sadegh-it".toUri())
            context.startActivity(intent)
        }
        DrawerItem.Times -> {
            navController.navigate(Screens.Times.route) {
                popUpTo(Screens.Home.route) { inclusive = false }
                launchSingleTop = true
            }
        }
        DrawerItem.Favorites -> {
            navController.navigate(Screens.Favorites.route) {
                popUpTo(Screens.Home.route) { inclusive = false }
                launchSingleTop = true
            }
        }
        DrawerItem.Settings -> {
            // TODO: SettingsScreen
        }
        DrawerItem.Resources -> {
            val intent = Intent(Intent.ACTION_VIEW, "https://setare.com/c/17/".toUri())
            context.startActivity(intent)
        }
        DrawerItem.Exit -> {
            (context as Activity).finish()
        }
    }
}