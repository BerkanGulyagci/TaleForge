package com.berkang.storyteler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_history")
data class StoryHistoryEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val characterName: String,
    val characterAnimationRes: Int,
    val genre: String,
    val createdAt: Long
)
