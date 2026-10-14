package com.zhizhin.starwarspractice.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhizhin.starwarspractice.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var currentSearch = ""

    init {
        loadCharacters()
    }

    fun loadCharacters(
        page: Int = currentPage,
        search: String = currentSearch
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val characters = getCharactersUseCase(
                    page = page,
                    search = search
                )

                currentPage = page
                currentSearch = search

                _uiState.value = CharactersUiState(
                    characters = characters,
                    isLoading = false
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Не удалось загрузить персонажей. Проверьте подключение к интернету."
                )
            }
        }
    }

    fun searchCharacters(query: String) {
        loadCharacters(
            page = 1,
            search = query.trim()
        )
    }

    fun retry() {
        loadCharacters()
    }
}