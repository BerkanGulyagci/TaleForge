package com.berkang.storyteler.presentation.screens.story_player.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TtsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var textToSpeech: TextToSpeech? = null
    private var isInitialized = false
    
    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()
    
    var onSentenceCompleted: (() -> Unit)? = null
    
    init {
        initializeTts()
    }
    
    private fun initializeTts() {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.let { tts ->
                    val result = tts.setLanguage(Locale("tr", "TR"))
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        tts.setLanguage(Locale.getDefault())
                    }
                    
                    tts.setSpeechRate(0.85f)
                    tts.setPitch(1.0f)
                    
                    tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onStart(utteranceId: String?) {
                            _isSpeaking.value = true
                        }
                        
                        override fun onDone(utteranceId: String?) {
                            _isSpeaking.value = false
                            onSentenceCompleted?.invoke()
                        }
                        
                        override fun onError(utteranceId: String?) {
                            _isSpeaking.value = false
                            onSentenceCompleted?.invoke()
                        }
                    })
                    
                    isInitialized = true
                }
            }
        }
    }
    
    fun speak(text: String) {
        if (!isInitialized) return
        
        textToSpeech?.let { tts ->
            val params = HashMap<String, String>()
            params[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "story_utterance"
            
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, params)
        }
    }
    
    fun stop() {
        if (!isInitialized) return
        
        textToSpeech?.let { tts ->
            tts.stop()
            _isSpeaking.value = false
        }
    }
    
    fun shutdown() {
        textToSpeech?.let { tts ->
            tts.stop()
            tts.shutdown()
        }
        textToSpeech = null
        isInitialized = false
        _isSpeaking.value = false
        onSentenceCompleted = null
    }
}