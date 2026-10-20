package com.zhizhin.starwarspractice.data

import com.zhizhin.starwarspractice.data.remote.RetrofitInstance
import com.zhizhin.starwarspractice.domain.model.Character
import com.zhizhin.starwarspractice.domain.repository.CharacterRepository

class CharacterRepositoryImpl : CharacterRepository {

    override suspend fun getCharacters(
        page: Int,
        search: String
    ): List<Character> {
        val response = RetrofitInstance.characterApi.getCharacters(
            page = page,
            search = search
        )

        return response.results.map { dto ->
            val id = dto.url
                .trimEnd('/')
                .substringAfterLast('/')
                .toIntOrNull()
                ?: throw IllegalStateException(
                    "Не удалось определить ID персонажа: ${dto.url}"
                )

            Character(
                id = id,
                name = dto.name,
                height = dto.height,
                mass = dto.mass,
                hairColor = dto.hairColor,
                skinColor = dto.skinColor,
                eyeColor = dto.eyeColor,
                birthYear = dto.birthYear,
                gender = dto.gender,
                homeworld = dto.homeworld
            )
        }
    }
}