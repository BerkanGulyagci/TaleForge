package com.berkang.storyteler.di

import com.berkang.storyteler.data.repository.CharacterRepositoryImpl
import com.berkang.storyteler.data.repository.FakeStoryRepository
import com.berkang.storyteler.data.repository.StoryRepositoryImpl
import com.berkang.storyteler.domain.repository.CharacterRepository
import com.berkang.storyteler.domain.repository.StoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository
    
    @Binds
    @Singleton
    abstract fun bindStoryRepository(
        fakeStoryRepository: FakeStoryRepository
    ): StoryRepository
}