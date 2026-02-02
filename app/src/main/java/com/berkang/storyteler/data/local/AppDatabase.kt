package com.berkang.storyteler.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.berkang.storyteler.data.local.dao.StoryHistoryDao
import com.berkang.storyteler.data.local.entity.StoryHistoryEntity

@Database(entities = [StoryHistoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storyHistoryDao(): StoryHistoryDao
}
