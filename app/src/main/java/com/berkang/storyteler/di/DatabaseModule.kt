package com.berkang.storyteler.di

import android.content.Context
import androidx.room.Room
import com.berkang.storyteler.data.local.AppDatabase
import com.berkang.storyteler.data.local.dao.StoryHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "story_teler_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideStoryHistoryDao(database: AppDatabase): StoryHistoryDao {
        return database.storyHistoryDao()
    }
}
