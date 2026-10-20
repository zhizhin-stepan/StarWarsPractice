package com.zhizhin.starwarspractice.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhizhin.starwarspractice.domain.model.Character
import com.zhizhin.starwarspractice.domain.repository.FavoriteRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class FavoritesViewModel(
    private val repository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            repository.favorites
                .catch { exception ->
                    if (exception is CancellationException) {
                        throw exception
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Не удалось загрузить избранное."
                    )
                }
                .collect { characters ->
                    _uiState.value = FavoritesUiState(
                        characters = characters,
                        isLoading = false
                    )

                    _favoriteIds.value = characters.map { it.id }.toSet()
                }
        }
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(character)
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Не удалось изменить избранное."
                )
            }
        }
    }

    fun removeFavorite(characterId: Int) {
        viewModelScope.launch {
            try {
                repository.removeFavorite(characterId)
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Не удалось удалить персонажа из избранного."
                )
            }
        }
    }
}