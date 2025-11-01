package io.github.sadeghit.dream.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadeghit.dream.data.model.DreamLetter
import io.github.sadeghit.dream.data.repository.DreamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DreamViewModel @Inject constructor(
    private val repository: DreamRepository
) : ViewModel() {

    private val _dream = MutableStateFlow<List<DreamLetter>>(emptyList())
    val dream: StateFlow<List<DreamLetter>> = _dream

    init {
        loadDreams()
    }

    private fun loadDreams() {
        viewModelScope.launch {
            val raw = repository.loadDreams()
            // مرتب‌سازی یک‌بار در زمان لود
            val sorted = raw
                .sortedBy { it.letter }                     // حروف الفبا
                .map { letter ->
                    letter.copy(
                        words = letter.words?.sortedBy { it.word } // کلمات داخل هر حرف
                    )
                }
            _dream.value = sorted
        }
    }
}