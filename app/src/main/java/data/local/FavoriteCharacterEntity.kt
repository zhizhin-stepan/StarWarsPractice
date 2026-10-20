package com.zhizhin.starwarspractice.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhizhin.starwarspractice.domain.model.Character

@Entity(tableName = "favorite_characters")
data class FavoriteCharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val homeworld: String
)

fun FavoriteCharacterEntity.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        height = height,
        mass = mass,
        hairColor = hairColor,
        skinColor = skinColor,
        eyeColor = eyeColor,
        birthYear = birthYear,
        gender = gender,
        homeworld = homeworld
    )
}

fun Character.toEntity(): FavoriteCharacterEntity {
    return FavoriteCharacterEntity(
        id = id,
        name = name,
        height = height,
        mass = mass,
        hairColor = hairColor,
        skinColor = skinColor,
        eyeColor = eyeColor,
        birthYear = birthYear,
        gender = gender,
        homeworld = homeworld
    )
}