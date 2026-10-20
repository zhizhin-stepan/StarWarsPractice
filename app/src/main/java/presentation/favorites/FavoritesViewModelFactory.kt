package com.zhizhin.starwarspractice.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhizhin.starwarspractice.domain.repository.FavoriteRepository

class FavoritesViewModelFactory(
    private val repository: FavoriteRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Неизвестный класс ViewModel: ${modelClass.name}"
        )
    }
}