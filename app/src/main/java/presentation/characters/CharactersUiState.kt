package com.zhizhin.starwarspractice.presentation.characters

import com.zhizhin.starwarspractice.domain.model.Character

data class CharactersUiState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)