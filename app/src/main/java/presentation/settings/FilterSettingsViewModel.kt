package com.zhizhin.starwarspractice.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhizhin.starwarspractice.domain.model.FilterSettings
import com.zhizhin.starwarspractice.domain.repository.FilterSettingsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class FilterSettingsUiState(
    val name: String = "",
    val gender: String = "ALL",
    val minHeight: String = "0",
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val saved: Boolean = false,
    val error: String? = null
) {
    val hasActiveFilters: Boolean
        get() = name.isNotBlank() ||
                gender != "ALL" ||
                (minHeight.toIntOrNull() ?: 0) > 0
}

class FilterSettingsViewModel(
    private val repository: FilterSettingsRepository,
    private val badgeCache: FilterBadgeCache
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilterSettingsUiState())
    val uiState: StateFlow<FilterSettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            try {
                val settings = repository.settings.first()

                badgeCache.update(settings)

                _uiState.value = FilterSettingsUiState(
                    name = settings.name,
                    gender = settings.gender,
                    minHeight = settings.minHeight.toString(),
                    isLoading = false
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Не удалось загрузить настройки."
                )
            }
        }
    }

    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(
            name = value,
            saved = false,
            error = null
        )
    }

    fun onGenderChange(value: String) {
        _uiState.value = _uiState.value.copy(
            gender = value,
            saved = false,
            error = null
        )
    }

    fun onMinHeightChange(value: String) {
        val filteredValue = value.filter { it.isDigit() }.take(3)

        _uiState.value = _uiState.value.copy(
            minHeight = filteredValue,
            saved = false,
            error = null
        )
    }

    fun saveSettings(onSaved: () -> Unit) {
        val state = _uiState.value
        val height = state.minHeight.toIntOrNull() ?: 0

        if (height !in 0..300) {
            _uiState.value = state.copy(
                error = "Минимальный рост должен быть от 0 до 300 см."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(
                isSaving = true,
                error = null,
                saved = false
            )

            try {
                val settings = FilterSettings(
                    name = state.name.trim(),
                    gender = state.gender,
                    minHeight = height
                )

                repository.saveSettings(settings)
                badgeCache.update(settings)

                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    saved = true
                )

                onSaved()
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    error = "Не удалось сохранить настройки."
                )
            }
        }
    }

    fun resetSettings() {
        viewModelScope.launch {
            try {
                repository.clearSettings()
                badgeCache.clear()

                _uiState.value = FilterSettingsUiState(
                    isLoading = false
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Не удалось сбросить настройки."
                )
            }
        }
    }
}