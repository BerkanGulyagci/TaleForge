package com.berkang.storyteler.domain.usecase

import com.berkang.storyteler.domain.model.Character
import com.berkang.storyteler.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Deprecated("Karakter seçimi şu an UI seviyesinde yönetiliyor.")
class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(): Flow<List<Character>> {
        return characterRepository.getCharacters()
    }
}