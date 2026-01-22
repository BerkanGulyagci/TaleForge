package com.berkang.storyteler.presentation.screens.story_setup

data class StorySetupUiState(
    val topic: String = "",
    val genre: String = "Adventure",
    val length: String = "Short",
    val targetAge: Int = 5,
    val notes: String = "",
    val isValid: Boolean = false
)