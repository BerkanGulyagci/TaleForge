package com.berkang.storyteler.domain.model

data class StoryHistory(
    val id: String,
    val title: String,
    val content: String,
    val characterName: String,
    val characterAnimationRes: Int,
    val genre: String,
    val createdAt: Long
)
