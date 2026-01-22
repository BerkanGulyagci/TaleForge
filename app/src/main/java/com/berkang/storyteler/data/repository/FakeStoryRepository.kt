package com.berkang.storyteler.data.repository

import com.berkang.storyteler.domain.model.Character
import com.berkang.storyteler.domain.model.Story
import com.berkang.storyteler.domain.model.StoryParams
import com.berkang.storyteler.domain.model.StoryRequest
import com.berkang.storyteler.domain.repository.StoryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeStoryRepository @Inject constructor() : StoryRepository {
    
    private val stories = mutableListOf<Story>()
    
    override suspend fun generateStory(params: StoryParams, character: Character): Story {
        // Simüle edilmiş gecikme
        delay(1500)
        
        val storyContent = generateFakeStoryContent(params, character)
        
        return Story(
            id = UUID.randomUUID().toString(),
            title = "${params.topic} Masalı",
            content = storyContent,
            genre = mapGenreToEnum(params.genre),
            length = mapLengthToEnum(params.length),
            targetAge = params.targetAge,
            characterId = character.id,
            isCompleted = false,
            createdAt = System.currentTimeMillis()
        )
    }
    
    override suspend fun generateStory(request: StoryRequest): Result<Story> {
        return try {
            delay(1500)
            
            val story = Story(
                id = UUID.randomUUID().toString(),
                title = "${request.topic} Masalı",
                content = "Bu eski format ile oluşturulmuş bir masal...",
                genre = request.genre,
                length = request.length,
                targetAge = request.targetAge,
                characterId = request.characterId,
                isCompleted = false,
                createdAt = System.currentTimeMillis()
            )
            
            Result.success(story)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun saveStory(story: Story): Result<Unit> {
        return try {
            stories.removeAll { it.id == story.id }
            stories.add(story)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getStories(): Flow<List<Story>> {
        return flowOf(stories.toList())
    }
    
    override suspend fun getStoryById(id: String): Story? {
        return stories.find { it.id == id }
    }
    
    override suspend fun deleteStory(id: String): Result<Unit> {
        return try {
            stories.removeAll { it.id == id }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun generateFakeStoryContent(params: StoryParams, character: Character): String {
        val ageGroup = when {
            params.targetAge <= 5 -> "küçük"
            params.targetAge <= 8 -> "orta yaş"
            else -> "büyük"
        }
        
        val lengthDescription = when (params.length.lowercase()) {
            "short" -> "kısa ve öz"
            "medium" -> "orta uzunlukta"
            "long" -> "uzun ve detaylı"
            else -> "güzel"
        }
        
        val genreStyle = when (params.genre.lowercase()) {
            "adventure" -> "heyecan dolu macera"
            "fairy tale" -> "büyülü peri masalı"
            "educational" -> "öğretici hikaye"
            "funny" -> "komik ve eğlenceli"
            "friendship" -> "dostluk dolu"
            else -> "güzel"
        }
        
        return """
            Merhaba sevgili dinleyici! Ben ${character.name} ve sana ${lengthDescription} bir ${genreStyle} anlatacağım.
            
            ${params.topic} hakkında ${ageGroup} çocuklar için özel olarak hazırladığım bu hikaye...
            
            Bir zamanlar, çok uzak bir ülkede ${params.topic} ile ilgili muhteşem olaylar yaşanırmış. 
            Bu ${params.genre.lowercase()} türündeki hikayemizde, ${params.targetAge} yaşındaki çocukların 
            çok seveceği karakterler ve olaylar var.
            
            ${if (params.notes.isNotEmpty()) "Özel olarak belirttiğin: ${params.notes}" else ""}
            
            Hikayemiz burada başlıyor... 
            
            (Bu örnek bir fake masal içeriğidir. Gerçek uygulamada AI tarafından üretilecektir.)
            
            Ve böylece hikayemiz mutlu sonla biter. Umarım beğenmişsindir!
            
            - ${character.name} ❤️
        """.trimIndent()
    }
    
    private fun mapGenreToEnum(genre: String): com.berkang.storyteler.domain.model.StoryGenre {
        return when (genre.lowercase()) {
            "adventure" -> com.berkang.storyteler.domain.model.StoryGenre.ADVENTURE
            "fairy tale" -> com.berkang.storyteler.domain.model.StoryGenre.FAIRY_TALE
            "educational" -> com.berkang.storyteler.domain.model.StoryGenre.EDUCATIONAL
            "funny" -> com.berkang.storyteler.domain.model.StoryGenre.FUNNY
            "friendship" -> com.berkang.storyteler.domain.model.StoryGenre.FRIENDSHIP
            else -> com.berkang.storyteler.domain.model.StoryGenre.ADVENTURE
        }
    }
    
    private fun mapLengthToEnum(length: String): com.berkang.storyteler.domain.model.StoryLength {
        return when (length.lowercase()) {
            "short" -> com.berkang.storyteler.domain.model.StoryLength.SHORT
            "medium" -> com.berkang.storyteler.domain.model.StoryLength.MEDIUM
            "long" -> com.berkang.storyteler.domain.model.StoryLength.LONG
            else -> com.berkang.storyteler.domain.model.StoryLength.SHORT
        }
    }
}