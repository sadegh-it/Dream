
package io.github.sadeghit.dream.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.sadeghit.dream.data.model.TimeItem
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.io.bufferedReader
import kotlin.io.readText
import kotlin.io.use

@Singleton
class TimeRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun loadTimes(): List<TimeItem> {
        return try {
            val json = context.assets.open("times.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<TimeItem>>() {}.type
            Gson().fromJson(json, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}