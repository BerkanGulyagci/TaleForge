package com.berkang.storyteler.domain.model

@Deprecated("Domain katmanında request kullanılmaz. StoryParams kullanılmalı.")
data class StoryRequest(
    val topic: String,
    val genre: StoryGenre,
    val length: StoryLength,
    val targetAge: Int,
    val characterId: String,
    val additionalNotes: String = ""
)