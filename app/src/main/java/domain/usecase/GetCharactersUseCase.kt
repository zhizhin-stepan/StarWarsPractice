package com.zhizhin.starwarspractice.domain.usecase

import com.zhizhin.starwarspractice.domain.model.Character
import com.zhizhin.starwarspractice.domain.repository.CharacterRepository

class GetCharactersUseCase(
    private val repository: CharacterRepository
) {

    suspend operator fun invoke(
        page: Int = 1,
        search: String = ""
    ): List<Character> {
        return repository.getCharacters(page, search)
    }
}