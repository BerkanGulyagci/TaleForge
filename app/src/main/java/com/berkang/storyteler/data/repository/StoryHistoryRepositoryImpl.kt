package com.berkang.storyteler.data.repository

import com.berkang.storyteler.data.local.dao.StoryHistoryDao
import com.berkang.storyteler.data.local.entity.StoryHistoryEntity
import com.berkang.storyteler.domain.model.StoryHistory
import com.berkang.storyteler.domain.repository.StoryHistoryRepository
import javax.inject.Inject

class StoryHistoryRepositoryImpl @Inject constructor(
    private val dao: StoryHistoryDao
) : StoryHistoryRepository {

    override suspend fun saveStory(history: StoryHistory) {
        val entity = StoryHistoryEntity(
            id = history.id,
            title = history.title,
            content = history.content,
            characterName = history.characterName,
            characterAnimationRes = history.characterAnimationRes,
            genre = history.genre,
            createdAt = history.createdAt
        )
        dao.insert(entity)
    }

    override suspend fun getStories(): List<StoryHistory> {
        val entities = dao.getAll()
        return entities.map { entity ->
            StoryHistory(
                id = entity.id,
                title = entity.title,
                content = entity.content,
                characterName = entity.characterName,
                characterAnimationRes = entity.characterAnimationRes,
                genre = entity.genre,
                createdAt = entity.createdAt
            )
        }
    }
}
