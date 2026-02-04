package com.berkang.storyteler.domain.usecase

import com.berkang.storyteler.domain.model.Story
import com.berkang.storyteler.domain.repository.StoryRepository
import javax.inject.Inject

class GenerateStoryUseCase @Inject constructor(
    private val storyRepository: StoryRepository
) {
    suspend operator fun invoke(userPrompt: String): Story {
        return storyRepository.generateStory(userPrompt)
    }
}