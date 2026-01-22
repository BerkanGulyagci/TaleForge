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
class StoryRepositoryImpl @Inject constructor() : StoryRepository {
    
    private val stories = mutableListOf<Story>()
    override suspend fun generateStory(
        params: StoryParams,
        character: Character
    ): Story {
        TODO("Not yet implemented")
    }

    override suspend fun generateStory(request: StoryRequest): Result<Story> {
        return try {
            // Simüle edilmiş AI hikaye üretimi
            delay(2000) // 2 saniye bekleme
            
            val story = Story(
                id = UUID.randomUUID().toString(),
                title = "${request.topic} Masalı",
                content = generateSampleStoryContent(request),
                genre = request.genre,
                length = request.length,
                targetAge = request.targetAge,
                characterId = request.characterId,
                isCompleted = false
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
    
    private fun generateSampleStoryContent(request: StoryRequest): String {
        return """
            Bir zamanlar, ${request.topic} hakkında güzel bir hikaye varmış...
            
            Bu hikaye ${request.genre.displayName.lowercase()} türünde ve ${request.targetAge} yaşındaki çocuklar için özel olarak hazırlanmış.
            
            Hikayemiz yaklaşık ${request.length.estimatedMinutes} dakika sürecek ve çok eğlenceli olacak!
            
            ${if (request.additionalNotes.isNotEmpty()) "Özel notlar: ${request.additionalNotes}" else ""}
            
            (Bu örnek bir hikaye içeriğidir. Gerçek uygulamada AI tarafından üretilecektir.)
        """.trimIndent()
    }
}