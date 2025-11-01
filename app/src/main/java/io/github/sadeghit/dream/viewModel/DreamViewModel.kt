package io.github.sadeghit.dream.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadeghit.dream.data.model.DreamLetter
import io.github.sadeghit.dream.data.repository.DreamRepository
import io.github.sadeghit.dream.util.PERSIAN_LETTER_ORDER
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

            val sorted = raw
                .sortedWith(compareBy { letter ->
                    val index = PERSIAN_LETTER_ORDER.indexOf(letter.letter)
                    if (index != -1) index else PERSIAN_LETTER_ORDER.size
                })
                .map { letter ->
                    letter.copy(
                        words = letter.words?.sortedBy { it.word }
                    )
                }
            _dream.value = sorted
        }
    }

}