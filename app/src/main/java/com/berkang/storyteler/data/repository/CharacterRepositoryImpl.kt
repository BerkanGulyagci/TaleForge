package com.berkang.storyteler.data.repository

import com.berkang.storyteler.R
import com.berkang.storyteler.domain.model.Character
import com.berkang.storyteler.domain.model.VoiceType
import com.berkang.storyteler.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor() : CharacterRepository {
    
    private val characters = listOf(
        Character(
            id = "santa",
            name = "Noel Baba",
            description = "Sevimli ve bilge Noel Baba, çocuklara en güzel masalları anlatır.",
            imageRes = R.drawable.ic_launcher_foreground,
            animationRes = R.raw.santa,
            voiceType = VoiceType.WISE
        ),
        Character(
            id = "teddy_bear",
            name = "Peluş Ayı",
            description = "Yumuşacık peluş ayı, sıcacık masallarla çocukları uyutur.",
            imageRes = R.drawable.ic_launcher_foreground,
            animationRes = R.raw.bear,
            voiceType = VoiceType.FRIENDLY
        ),
        Character(
            id = "fairy",
            name = "Peri",
            description = "Büyülü peri, renkli masallarla hayal dünyasını genişletir.",
            imageRes = R.drawable.ic_launcher_foreground,
            animationRes = R.raw.bunny, // Geçici olarak
            voiceType = VoiceType.MAGICAL
        ),
        Character(
            id = "wise_owl",
            name = "Bilge Baykuş",
            description = "Akıllı baykuş, öğretici masallarla bilgi dolu hikayeler anlatır.",
            imageRes = R.drawable.ic_launcher_foreground,
            animationRes = R.raw.robot, // Geçici olarak
            voiceType = VoiceType.WISE
        ),
        Character(
            id = "happy_rabbit",
            name = "Neşeli Tavşan",
            description = "Enerjik tavşan, eğlenceli masallarla kahkaha dolu anlar yaşatır.",
            imageRes = R.drawable.ic_launcher_foreground,
            animationRes = R.raw.bunny,
            voiceType = VoiceType.CHEERFUL
        )
    )
    
    override suspend fun getCharacters(): Flow<List<Character>> {
        return flowOf(characters)
    }
    
    override suspend fun getCharacterById(id: String): Character? {
        return characters.find { it.id == id }
    }
}