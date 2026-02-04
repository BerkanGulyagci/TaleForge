package com.berkang.storyteler.domain.repository

import com.berkang.storyteler.domain.model.StoryHistory

interface StoryHistoryRepository {
    suspend fun saveStory(history: StoryHistory)
    suspend fun getStories(): List<StoryHistory>
    suspend fun getStoryById(id: String): StoryHistory?
}
