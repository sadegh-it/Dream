package io.github.sadeghit.dream.viewModel

import android.R.attr.data
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadeghit.dream.data.model.TimeItem
import io.github.sadeghit.dream.data.repository.TimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class TimeViewModel @Inject constructor(
    private val repository: TimeRepository
) : ViewModel() {
    private val _times = MutableStateFlow<List<TimeItem>>(emptyList())
    val times: StateFlow<List<TimeItem>> = _times

    init {
        loadTimes()
    }

    private fun loadTimes() {
        viewModelScope.launch {
            val data = repository.loadTimes()
            println("DEBUG: Times loaded, size = ${data.size}")  // اینو ببین!
            _times.value = data
        }

    }

}