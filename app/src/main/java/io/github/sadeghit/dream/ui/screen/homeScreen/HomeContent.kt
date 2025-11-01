package io.github.sadeghit.dream.ui.screen.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import io.github.sadeghit.dream.data.model.DreamWord
import io.github.sadeghit.dream.ui.component.LetterList
import io.github.sadeghit.dream.ui.component.SearchBar
import io.github.sadeghit.dream.ui.component.WordList

@Composable
fun HomeContent(
    padding: PaddingValues,
    state: HomeScreenState,
    letters: List<String>,
    onLetterClick: (String) -> Unit,
    onWordClick: (DreamWord) -> Unit,
    onSearchChange: (String) -> Unit,
    focusManager: FocusManager
) {
    Row(
    modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { focusManager.clearFocus() }
    ) {
        Column(modifier = Modifier.weight(0.7f)) {
            SearchBar(
                query = state.searchQuery.value,
                onQueryChange = onSearchChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
            WordList(
                words = state.filteredWords,
                onWordClick = onWordClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )
        }
        LetterList(
            letters = letters,
            selectedLetter = state.selectedLetter.value,
            onLetterClick = onLetterClick,
            modifier = Modifier.weight(0.3f)
        )
    }
}