// FavoritesViewModel.kt
package io.github.sadeghit.dream.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadeghit.dream.data.dataStore.FavoritesDataStore

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesDataStore: FavoritesDataStore
) : ViewModel() {

    // State برای نگهداری لیست علاقه‌مندی‌ها در Compose
    var favorites = mutableStateOf<Set<String>>(emptySet())
        private set

    init {
        // خواندن علاقه‌مندی‌ها و بروز رسانی State
        favoritesDataStore.favorites
            .onEach { set -> favorites.value = set }
            .launchIn(viewModelScope)
    }

    fun addFavorite(item: String) {
        viewModelScope.launch {
            favoritesDataStore.addFavorite(item)
        }
    }

    fun removeFavorite(item: String) {
        viewModelScope.launch {
            favoritesDataStore.removeFavorite(item)
        }
    }


    fun clearFavorites() {
        viewModelScope.launch {
            favoritesDataStore.clearFavorites()
        }
    }
}
