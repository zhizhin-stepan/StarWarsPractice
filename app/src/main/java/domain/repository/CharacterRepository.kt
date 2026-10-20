package com.zhizhin.starwarspractice.domain.repository

import com.zhizhin.starwarspractice.domain.model.Character

interface CharacterRepository {

    suspend fun getCharacters(
        page: Int = 1,
        search: String = ""
    ): List<Character>
}