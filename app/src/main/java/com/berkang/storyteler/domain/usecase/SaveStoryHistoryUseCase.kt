package com.berkang.storyteler.domain.usecase

import com.berkang.storyteler.domain.model.StoryHistory
import com.berkang.storyteler.domain.repository.StoryHistoryRepository

import javax.inject.Inject

class SaveStoryHistoryUseCase @Inject constructor(
    private val repository: StoryHistoryRepository
) {
    suspend operator fun invoke(history: StoryHistory) {
        repository.saveStory(history)
    }
}
