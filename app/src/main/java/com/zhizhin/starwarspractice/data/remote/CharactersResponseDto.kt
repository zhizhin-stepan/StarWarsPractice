package com.zhizhin.starwarspractice.data.remote

data class CharactersResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<CharacterDto>
)