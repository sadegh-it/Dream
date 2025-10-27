package io.github.sadeghit.dream.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.sadeghit.dream.data.model.DreamLetter
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DreamRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun loadDreams(): List<DreamLetter> {
        return try {
            val json = context.assets.open("dream.json").bufferedReader()
                .use { it.readText() }
            val type = object : TypeToken<List<DreamLetter>>() {}.type
            Gson().fromJson(json, type)
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }
}