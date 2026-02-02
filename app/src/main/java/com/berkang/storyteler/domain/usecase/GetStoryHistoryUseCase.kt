package com.berkang.storyteler.domain.usecase

import com.berkang.storyteler.domain.model.StoryHistory
import com.berkang.storyteler.domain.repository.StoryHistoryRepository

import javax.inject.Inject

class GetStoryHistoryUseCase @Inject constructor(
    private val repository: StoryHistoryRepository
) {
    suspend operator fun invoke(): List<StoryHistory> {
        return repository.getStories()
    }
}
