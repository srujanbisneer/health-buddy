package com.healthbuddy.chatbot

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TypeWriter @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var fullText: CharSequence? = null
    private var currentIndex = 0
    private var characterDelay: Long = 150 // default 150ms delay
    private val handler = Handler(Looper.getMainLooper())

    private val characterAdder = object : Runnable {
        override fun run() {
            if (fullText != null && currentIndex <= fullText!!.length) {
                text = fullText!!.subSequence(0, currentIndex++)
                handler.postDelayed(this, characterDelay)
            }
        }
    }

    fun animateText(text: CharSequence?) {
        fullText = text
        currentIndex = 0
        setText("")
        handler.removeCallbacks(characterAdder)
        handler.postDelayed(characterAdder, characterDelay)
    }

    fun setCharacterDelay(millis: Long) {
        characterDelay = millis
    }
}