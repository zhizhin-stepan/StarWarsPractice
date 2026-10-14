package com.zhizhin.starwarspractice.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhizhin.starwarspractice.data.CharacterRepositoryImpl
import com.zhizhin.starwarspractice.domain.usecase.GetCharactersUseCase

class CharactersViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            val repository = CharacterRepositoryImpl()
            val useCase = GetCharactersUseCase(repository)

            @Suppress("UNCHECKED_CAST")
            return CharactersViewModel(useCase) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}