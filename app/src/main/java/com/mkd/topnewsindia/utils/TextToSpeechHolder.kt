package com.mkd.topnewsindia.utils

import android.content.Context
import android.speech.tts.TextToSpeech

class TextToSpeechHolder(context: Context) : TextToSpeech.OnInitListener {
    private var textToSpeech: TextToSpeech? = null
    private var isInitialized = false

    fun isInitialized() = isInitialized

    fun speak(text: String) {
        val splitText = splitText(text)
        splitText.forEach { chunk ->
            textToSpeech?.speak(chunk, TextToSpeech.QUEUE_ADD, null, null)
        }
    }

    fun stop() {
        textToSpeech?.stop()
    }

    fun release() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }

    fun setSpeed(speed: Float) {
        textToSpeech?.setSpeechRate(speed)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
        }
    }

    init {
        textToSpeech = TextToSpeech(context, this)
    }
}

fun splitText(text: String, chunkSize: Int = 4000): List<String> {
    val cleanText = text.replace("\n", "").trim()
    val chunks = mutableListOf<String>()
    var start = 0
    while (start < cleanText.length) {
        val end = cleanText.length.coerceAtMost(start + chunkSize)
        chunks.add(cleanText.substring(start, end))
        start = end
    }
    return chunks
}
