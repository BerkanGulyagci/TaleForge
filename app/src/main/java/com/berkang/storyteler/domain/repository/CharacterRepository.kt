package com.berkang.storyteler.domain.repository

import com.berkang.storyteler.domain.model.Character
import kotlinx.coroutines.flow.Flow

@Deprecated("Karakterler şu an statik. Ayrı repository ileride değerlendirilecek.")
interface CharacterRepository {
    suspend fun getCharacters(): Flow<List<Character>>
    suspend fun getCharacterById(id: String): Character?
}