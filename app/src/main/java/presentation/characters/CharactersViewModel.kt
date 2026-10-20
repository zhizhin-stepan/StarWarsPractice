package com.zhizhin.starwarspractice.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhizhin.starwarspractice.domain.model.Character
import com.zhizhin.starwarspractice.domain.model.FilterSettings
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
    private var filterSettings = FilterSettings()
    private var loadedCharacters: List<Character> = emptyList()

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
                val effectiveSearch = search.ifBlank {
                    filterSettings.name.trim()
                }

                val characters = getCharactersUseCase(
                    page = page,
                    search = effectiveSearch
                )

                currentPage = page
                currentSearch = search
                loadedCharacters = characters

                updateFilteredCharacters()
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

    fun setFilterSettings(settings: FilterSettings) {
        val previousSettings = filterSettings
        filterSettings = settings

        if (previousSettings.name.trim() != settings.name.trim()) {
            loadCharacters(page = 1, search = "")
        } else {
            updateFilteredCharacters()
        }
    }

    private fun updateFilteredCharacters() {
        val settings = filterSettings

        val filtered = loadedCharacters.filter { character ->
            val matchesName = settings.name.isBlank() ||
                    character.name.contains(
                        settings.name.trim(),
                        ignoreCase = true
                    )

            val matchesGender = settings.gender == "ALL" ||
                    character.gender.equals(
                        settings.gender,
                        ignoreCase = true
                    ) ||
                    (
                            settings.gender == "other" &&
                                    !character.gender.equals("male", ignoreCase = true) &&
                                    !character.gender.equals("female", ignoreCase = true)
                            )

            val characterHeight = character.height.toIntOrNull()
            val matchesHeight = settings.minHeight <= 0 ||
                    (characterHeight != null &&
                            characterHeight >= settings.minHeight)

            matchesName && matchesGender && matchesHeight
        }

        _uiState.value = _uiState.value.copy(
            characters = filtered,
            isLoading = false,
            error = null
        )
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