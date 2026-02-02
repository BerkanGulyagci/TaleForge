package com.berkang.storyteler.presentation.screens.library

import com.berkang.storyteler.domain.model.StoryHistory

data class LibraryUiState(
    val stories: List<StoryHistory> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false
)
