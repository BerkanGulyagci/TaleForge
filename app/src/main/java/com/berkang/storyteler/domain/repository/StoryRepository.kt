package com.berkang.storyteler.domain.repository

import com.berkang.storyteler.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    suspend fun generateStory(userPrompt: String): Story
    suspend fun saveStory(story: Story): Result<Unit>
    suspend fun getStories(): Flow<List<Story>>
    suspend fun getStoryById(id: String): Story?
    suspend fun deleteStory(id: String): Result<Unit>
}