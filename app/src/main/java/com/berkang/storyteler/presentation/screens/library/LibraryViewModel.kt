package com.berkang.storyteler.presentation.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkang.storyteler.domain.usecase.GetStoryHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getStoryHistoryUseCase: GetStoryHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    init {
        loadStories()
    }

    private fun loadStories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val stories = getStoryHistoryUseCase()
                _uiState.value = _uiState.value.copy(
                    stories = stories,
                    isEmpty = stories.isEmpty(),
                    isLoading = false
                )
            } catch (e: Exception) {
                // Hata durumunda boş liste ve not loading
                _uiState.value = _uiState.value.copy(
                    stories = emptyList(),
                    isEmpty = true,
                    isLoading = false
                )
            }
        }
    }
}
