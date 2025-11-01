package io.github.sadeghit.dream.ui.screen.homeScreen

import androidx.compose.runtime.MutableState
import io.github.sadeghit.dream.data.model.DreamLetter
import io.github.sadeghit.dream.data.model.DreamWord
import io.github.sadeghit.dream.navigation.DrawerItem
import io.github.sadeghit.dream.util.normalizeText

// 1. interface با filteredWords به عنوان property
interface HomeScreenState {
    val selectedLetter: MutableState<String>
    val searchQuery: MutableState<String>
    val selectedDrawerItem: MutableState<DrawerItem>
    val filteredWords: List<DreamWord>  // فقط خواندنی
}

// 2. data class فقط stateهای قابل تغییر
data class HomeScreenStateData(
    override val selectedLetter: MutableState<String>,
    override val searchQuery: MutableState<String>,
    override val selectedDrawerItem: MutableState<DrawerItem>
) : HomeScreenState {
    // filteredWords رو اینجا نمی‌ذاریم → در Impl
    override val filteredWords: List<DreamWord> get() = error("Not implemented")
}

// 3. Implementation واقعی
class HomeScreenStateImpl(
    private val data: HomeScreenStateData,
    private val dreams: List<DreamLetter>
) : HomeScreenState by data {
    override val filteredWords: List<DreamWord>
        get() = if (data.searchQuery.value.isBlank()) {
            dreams.firstOrNull { it.letter == data.selectedLetter.value }?.words.orEmpty()
        } else {
            val q = normalizeText(data.searchQuery.value)
            dreams.flatMap { it.words.orEmpty() }
                .filter { normalizeText(it.word ?: "").contains(q, ignoreCase = true) }
        }
}