package com.berkang.storyteler.presentation.screens.story_setup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StorySetupViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(StorySetupUiState())
    val uiState: StateFlow<StorySetupUiState> = _uiState.asStateFlow()
    
    fun updateTopic(topic: String) {
        _uiState.value = _uiState.value.copy(
            topic = topic,
            isValid = topic.isNotBlank()
        )
    }
    
    fun updateGenre(genre: String) {
        _uiState.value = _uiState.value.copy(genre = genre)
    }
    
    fun updateLength(length: String) {
        _uiState.value = _uiState.value.copy(length = length)
    }
    
    fun updateTargetAge(age: Int) {
        _uiState.value = _uiState.value.copy(targetAge = age)
    }
    
    fun updateNotes(notes: String) {
        _uiState.value = _uiState.value.copy(notes = notes)
    }
}