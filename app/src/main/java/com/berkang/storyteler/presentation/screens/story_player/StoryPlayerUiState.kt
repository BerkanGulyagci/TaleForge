package com.berkang.storyteler.presentation.screens.story_player

import com.berkang.storyteler.domain.model.Story

data class StoryPlayerUiState(
    val isLoading: Boolean = false,
    val story: Story? = null,
    val error: String? = null
)