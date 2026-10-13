package com.zhizhin.starwarspractice.data

import com.zhizhin.starwarspractice.domain.StarWarsCharacter

interface CharacterRepository {
    fun getCharacters(): List<StarWarsCharacter>
}