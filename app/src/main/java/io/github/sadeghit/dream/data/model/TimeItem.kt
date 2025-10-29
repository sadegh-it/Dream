package io.github.sadeghit.dream.data.model

data class TimeItem(
    val word: String,
    val entries: List<TimeEntry>
)

data class TimeEntry(
    val title: String,
    val meaning: String
)