package com.berkang.storyteler.domain.model

data class StoryParams(
    val topic: String,
    val genre: String,
    val length: String,
    val targetAge: Int,
    val notes: String
)