package com.berkang.storyteler.domain.usecase

import com.berkang.storyteler.domain.model.Character
import com.berkang.storyteler.domain.model.Story
import com.berkang.storyteler.domain.model.StoryParams
import com.berkang.storyteler.domain.repository.StoryRepository
import javax.inject.Inject

class GenerateStoryUseCase @Inject constructor(
    private val storyRepository: StoryRepository
) {
    suspend operator fun invoke(params: StoryParams, character: Character): Story {
        return storyRepository.generateStory(params, character)
    }
}