package com.berkang.storyteler.presentation.screens.character_select

import androidx.lifecycle.ViewModel
import com.berkang.storyteler.R
import com.berkang.storyteler.domain.model.Character
import com.berkang.storyteler.domain.model.VoiceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterSelectViewModel @Inject constructor() : ViewModel() {
    
    private val _selectedCharacter = MutableStateFlow<Character?>(null)
    val selectedCharacter: StateFlow<Character?> = _selectedCharacter.asStateFlow()
    
    val characters = listOf(
        Character(
            id = "bear",
            name = "Peluş Ayı",
            description = "Yumuşacık peluş ayı, sıcacık masallarla çocukları uyutur.",
            imageRes = R.drawable.ic_launcher_foreground,
            animationRes = R.raw.bear,
            voiceType = VoiceType.FRIENDLY
        ),
        Character(
            id = "bunny",
            name = "Tavşan",
            description = "Enerjik tavşan, eğlenceli masallarla kahkaha dolu anlar yaşatır.",
            imageRes = R.drawable.ic_launcher_foreground,
            animationRes = R.raw.bunny,
            voiceType = VoiceType.CHEERFUL
        ),
        Character(
            id = "santa",
            name = "Noel Baba",
            description = "Sevimli ve bilge Noel Baba, çocuklara en güzel masalları anlatır.",
            imageRes = R.drawable.ic_launcher_foreground,
            animationRes = R.raw.santa,
            voiceType = VoiceType.WISE
        ),
        Character(
            id = "robot",
            name = "Robot",
            description = "Modern robot, teknoloji dolu masallarla geleceği anlatır.",
            imageRes = R.drawable.ic_launcher_foreground,
            animationRes = R.raw.robot,
            voiceType = VoiceType.CALM
        )
    )
    
    fun selectCharacter(character: Character) {
        _selectedCharacter.value = character
    }
}

