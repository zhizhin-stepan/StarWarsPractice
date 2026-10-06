package com.zhizhin.starwarspractice.presentation

import androidx.lifecycle.ViewModel
import com.zhizhin.starwarspractice.data.CharacterRepository
import com.zhizhin.starwarspractice.data.MockCharacterRepository
import com.zhizhin.starwarspractice.domain.StarWarsCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharactersViewModel(
    private val repository: CharacterRepository = MockCharacterRepository()
) : ViewModel() {

    private val _characters = MutableStateFlow(repository.getCharacters())

    val characters: StateFlow<List<StarWarsCharacter>> = _characters.asStateFlow()
}