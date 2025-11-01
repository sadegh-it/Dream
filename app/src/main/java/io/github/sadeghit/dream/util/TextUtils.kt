package io.github.sadeghit.dream.util

fun normalizeText(text: String): String =
    text.replace("ي", "ی").replace("ك", "ک").trim()