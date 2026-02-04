package com.berkang.storyteler.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berkang.storyteler.data.local.entity.StoryHistoryEntity

@Dao
interface StoryHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: StoryHistoryEntity)

    @Query("SELECT * FROM story_history ORDER BY createdAt DESC")
    suspend fun getAll(): List<StoryHistoryEntity>

    @Query("SELECT * FROM story_history WHERE id = :id")
    suspend fun getById(id: String): StoryHistoryEntity?
}
