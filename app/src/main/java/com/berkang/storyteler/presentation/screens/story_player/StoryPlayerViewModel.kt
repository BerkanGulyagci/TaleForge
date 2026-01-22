package com.berkang.storyteler.presentation.screens.story_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkang.storyteler.domain.model.Character
import com.berkang.storyteler.domain.model.StoryParams
import com.berkang.storyteler.domain.model.VoiceType
import com.berkang.storyteler.domain.usecase.GenerateStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryPlayerViewModel @Inject constructor(
    private val generateStoryUseCase: GenerateStoryUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(StoryPlayerUiState())
    val uiState: StateFlow<StoryPlayerUiState> = _uiState.asStateFlow()
    
    init {
        generateSampleStory()
    }
    
    private fun generateSampleStory() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                // Örnek parametreler (gerçek uygulamada navigation'dan gelecek)
                val sampleParams = StoryParams(
                    topic = "Uzay Macerası",
                    genre = "Adventure",
                    length = "Medium",
                    targetAge = 7,
                    notes = "Eğlenceli ve öğretici olsun"
                )
                
                val sampleCharacter = Character(
                    id = "teddy_bear",
                    name = "Peluş Ayı",
                    description = "Yumuşacık peluş ayı, sıcacık masallarla çocukları uyutur.",
                    imageRes = 0, // Placeholder
                    voiceType = VoiceType.FRIENDLY
                )
                
                val story = generateStoryUseCase(sampleParams, sampleCharacter)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    story = story,
                    error = null
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    story = null,
                    error = e.message ?: "Masal oluşturulurken bir hata oluştu"
                )
            }
        }
    }
}