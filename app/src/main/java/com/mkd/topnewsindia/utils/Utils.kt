package com.mkd.topnewsindia.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun extractTextFromUrl(url: String): String {
    return try {
        val doc = withContext(Dispatchers.IO) {
            Jsoup.connect(url).get()
        }
        doc.body().text()
    } catch (e: Exception) {
        Log.e("Utils", "Failed to fetch and extract text", e)
        ""
    }
}

enum class ButtonState {
    Play,
    Stop
}
