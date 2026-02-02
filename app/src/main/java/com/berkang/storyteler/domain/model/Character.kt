package com.berkang.storyteler.domain.model

data class Character(
    val id: String,
    val name: String,
    val description: String,
    val imageRes: Int,
    val animationRes: Int,
    val voiceType: VoiceType = VoiceType.FRIENDLY
)

enum class VoiceType(val displayName: String) {
    FRIENDLY("Arkadaş Canlısı"),
    WISE("Bilge"),
    CHEERFUL("Neşeli"),
    CALM("Sakin"),
    MAGICAL("Büyülü")
}