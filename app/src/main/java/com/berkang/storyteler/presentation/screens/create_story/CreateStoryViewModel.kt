package com.berkang.storyteler.presentation.screens.create_story

import androidx.lifecycle.ViewModel
import com.berkang.storyteler.domain.model.StoryGenre
import com.berkang.storyteler.domain.model.StoryLength
import com.berkang.storyteler.domain.model.StoryRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateStoryViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(CreateStoryUiState())
    val uiState: StateFlow<CreateStoryUiState> = _uiState.asStateFlow()
    
    fun updateTopic(topic: String) {
        _uiState.value = _uiState.value.copy(topic = topic)
    }
    
    fun updateGenre(genre: StoryGenre) {
        _uiState.value = _uiState.value.copy(selectedGenre = genre)
    }
    
    fun updateLength(length: StoryLength) {
        _uiState.value = _uiState.value.copy(selectedLength = length)
    }
    
    fun updateAge(age: Int) {
        _uiState.value = _uiState.value.copy(targetAge = age)
    }
    
    fun updateNotes(notes: String) {
        _uiState.value = _uiState.value.copy(additionalNotes = notes)
    }
    
    fun createStoryRequest(): StoryRequest? {
        val state = _uiState.value
        return if (state.topic.isNotBlank()) {
            StoryRequest(
                topic = state.topic,
                genre = state.selectedGenre,
                length = state.selectedLength,
                targetAge = state.targetAge,
                characterId = "", // Karakter seçimi sonraki adımda
                additionalNotes = state.additionalNotes
            )
        } else null
    }
    
    fun isFormValid(): Boolean {
        return _uiState.value.topic.isNotBlank()
    }
}

data class CreateStoryUiState(
    val topic: String = "",
    val selectedGenre: StoryGenre = StoryGenre.ADVENTURE,
    val selectedLength: StoryLength = StoryLength.SHORT,
    val targetAge: Int = 5,
    val additionalNotes: String = ""
)