package com.berkang.storyteler.data.repository

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
class StoryRepositoryImpl @Inject constructor() : StoryRepository {
    
    private val stories = mutableListOf<Story>()

    override suspend fun generateStory(userPrompt: String): Story {
        return try {
            // Simüle edilmiş AI hikaye üretimi
            delay(2000) // 2 saniye bekleme
            
            Story(
                id = UUID.randomUUID().toString(),
                title = "Story from RepositoryImpl",
                content = "This is a sample story generated for prompt: $userPrompt\n\nOnce upon a time...",
                genre = StoryGenre.ADVENTURE,
                length = StoryLength.MEDIUM,
                targetAge = 7,
                characterId = "repo_impl_narrator",
                isCompleted = false,
                createdAt = System.currentTimeMillis()
            )
        } catch (e: Exception) {
            throw e
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
}