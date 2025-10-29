package io.github.sadeghit.dream.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.sadeghit.dream.navigation.DrawerItem


@Composable
fun Drawer(
    currentItem: DrawerItem = DrawerItem.Times,
    onItemClick: (DrawerItem) -> Unit
) {


    val items = listOf(
        DrawerItem.Times,
        DrawerItem.Favorites,
        DrawerItem.Settings,
        DrawerItem.Resources,
        DrawerItem.Website,
        DrawerItem.Exit
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(250.dp)
            .background(colorScheme.surface)
            .padding(vertical = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "تعبیر خواب آبی 🌙",
                style = typography.titleMedium.copy(color = colorScheme.onPrimaryContainer)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        items.forEach { item ->
            val isSelected = item == currentItem
            val bgColor = if (isSelected) colorScheme.secondaryContainer else colorScheme.surface
            val textColor =
                if (isSelected) colorScheme.onSecondaryContainer else colorScheme.onSurface

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bgColor)
                    .clickable { onItemClick(item) }
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = item.title,
                    style = typography.bodyLarge.copy(color = textColor)
                )


            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        }
    }
}
