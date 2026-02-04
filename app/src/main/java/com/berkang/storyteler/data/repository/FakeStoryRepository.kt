package com.berkang.storyteler.data.repository

import com.berkang.storyteler.data.prompt.PromptBuilder
import com.berkang.storyteler.domain.model.Story
import com.berkang.storyteler.domain.model.StoryGenre
import com.berkang.storyteler.domain.model.StoryLength
import com.berkang.storyteler.domain.repository.StoryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeStoryRepository @Inject constructor(
    private val promptBuilder: PromptBuilder
) : StoryRepository {
    
    private val stories = mutableListOf<Story>()
    
    override suspend fun generateStory(userPrompt: String): Story {
        // Simüle edilmiş gecikme
        delay(1500)
        
        // 2. AI Prompt Oluşturma
        val fullAiPrompt = promptBuilder.build(userPrompt)
        
        // 3. Fake İçerik Üretimi
        val storyContent = """
            (Bu içerik PromptBuilder kullanılarak oluşturulan prompt üzerinden simüle edilmiştir.)
            
            Bir varmış, bir yokmuş...
            
            AI'ye Gönderilen Prompt:
            ---------------------------------------------------
            $fullAiPrompt
            ---------------------------------------------------
            
            Gökten üç elma düşmüş...
        """.trimIndent()
        
        return Story(
            id = UUID.randomUUID().toString(),
            title = "Fake Story",
            content = storyContent,
            genre = StoryGenre.ADVENTURE,
            length = StoryLength.MEDIUM,
            targetAge = 7,
            characterId = "fake_narrator",
            isCompleted = false,
            createdAt = System.currentTimeMillis()
        )
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
}