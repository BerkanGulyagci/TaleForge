package com.berkang.storyteler.domain.model

data class Story(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val genre: StoryGenre = StoryGenre.ADVENTURE,
    val length: StoryLength = StoryLength.SHORT,
    val targetAge: Int = 5,
    val characterId: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class StoryGenre(val displayName: String) {
    ADVENTURE("Macera"),
    FAIRY_TALE("Peri Masalı"),
    EDUCATIONAL("Eğitici"),
    FUNNY("Komik"),
    FRIENDSHIP("Dostluk")
}

enum class StoryLength(val displayName: String, val estimatedMinutes: Int) {
    SHORT("Kısa", 3),
    MEDIUM("Orta", 7),
    LONG("Uzun", 15)
}