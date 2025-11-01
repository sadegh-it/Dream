package io.github.sadeghit.dream.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("favorite")

@Singleton
class FavoritesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorites")
    }

    // Flow برای خواندن لیست علاقه‌مندی‌ها
    val favorites: Flow<Set<String>> = context.dataStore.data
        .map { it[FAVORITES_KEY] ?: emptySet() }

    // اضافه کردن علاقه‌مندی
    suspend fun addFavorite(item: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[FAVORITES_KEY] ?: emptySet()
            prefs[FAVORITES_KEY] = current + item
        }
    }

    // حذف یک علاقه‌مندی
    suspend fun removeFavorite(item: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[FAVORITES_KEY] ?: emptySet()
            prefs[FAVORITES_KEY] = current - item
        }
    }

    // پاک کردن همه علاقه‌مندی‌ها
    suspend fun clearFavorites() {
        context.dataStore.edit { prefs ->
            prefs[FAVORITES_KEY] = emptySet()
        }
    }
}
