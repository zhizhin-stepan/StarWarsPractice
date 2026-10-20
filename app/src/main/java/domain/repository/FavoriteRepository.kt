package com.zhizhin.starwarspractice.domain.repository

import com.zhizhin.starwarspractice.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    val favorites: Flow<List<Character>>

    suspend fun toggleFavorite(character: Character)

    suspend fun removeFavorite(characterId: Int)

    suspend fun isFavorite(characterId: Int): Boolean
}