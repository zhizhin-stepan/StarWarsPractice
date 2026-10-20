package com.zhizhin.starwarspractice.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {

    @Query(
        "SELECT * FROM favorite_characters ORDER BY name COLLATE NOCASE"
    )
    fun observeAll(): Flow<List<FavoriteCharacterEntity>>

    @Query("SELECT * FROM favorite_characters WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): FavoriteCharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: FavoriteCharacterEntity)

    @Query("DELETE FROM favorite_characters WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}