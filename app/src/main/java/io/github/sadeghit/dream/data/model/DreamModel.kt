package io.github.sadeghit.dream.data.model

data class DreamLetter(
    val letter: String,
    val words: List<DreamWord>? = emptyList()
)

data class DreamWord(
    val word: String? = "",
    val authors: List<DreamAuthor>? = emptyList()
)

data class DreamAuthor(
    val author: String? = "",
    val meaning: String? = ""
)
