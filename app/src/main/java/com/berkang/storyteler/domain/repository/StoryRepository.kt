package com.berkang.storyteler.domain.repository

import com.berkang.storyteler.domain.model.Character
import com.berkang.storyteler.domain.model.Story
import com.berkang.storyteler.domain.model.StoryParams
import com.berkang.storyteler.domain.model.StoryRequest
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    suspend fun generateStory(params: StoryParams, character: Character): Story
    suspend fun generateStory(request: StoryRequest): Result<Story>
    suspend fun saveStory(story: Story): Result<Unit>
    suspend fun getStories(): Flow<List<Story>>
    suspend fun getStoryById(id: String): Story?
    suspend fun deleteStory(id: String): Result<Unit>
}