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
    
    fun updatePrompt(prompt: String) {
        _uiState.value = _uiState.value.copy(
            prompt = prompt,
            isValid = prompt.isNotBlank()
        )
    }
}