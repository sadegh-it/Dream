package io.github.sadeghit.dream.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = {
            Text(
                "جستجو...",
                style = typography.bodyMedium.copy(
                    textAlign = TextAlign.Right,
                    textDirection = TextDirection.Rtl
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        trailingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                tint = colorScheme.onSurface
            )
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        textStyle = typography.bodyMedium.copy(
            textAlign = TextAlign.Right,
            textDirection = TextDirection.Rtl
        )
    )


}