package com.zhizhin.starwarspractice.data

import com.zhizhin.starwarspractice.data.local.FavoriteCharacterDao
import com.zhizhin.starwarspractice.data.local.toDomain
import com.zhizhin.starwarspractice.data.local.toEntity
import com.zhizhin.starwarspractice.domain.model.Character
import com.zhizhin.starwarspractice.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val dao: FavoriteCharacterDao
) : FavoriteRepository {

    override val favorites: Flow<List<Character>> =
        dao.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun toggleFavorite(character: Character) {
        if (dao.isFavorite(character.id)) {
            dao.deleteById(character.id)
        } else {
            dao.insert(character.toEntity())
        }
    }

    override suspend fun removeFavorite(characterId: Int) {
        dao.deleteById(characterId)
    }

    override suspend fun isFavorite(characterId: Int): Boolean {
        return dao.isFavorite(characterId)
    }
}