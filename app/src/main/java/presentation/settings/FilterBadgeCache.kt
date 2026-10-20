package com.zhizhin.starwarspractice.presentation.settings

import com.zhizhin.starwarspractice.domain.model.FilterSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FilterBadgeCache {

    private val _shouldShowBadge = MutableStateFlow(false)

    val shouldShowBadge: StateFlow<Boolean> =
        _shouldShowBadge.asStateFlow()

    fun update(settings: FilterSettings) {
        _shouldShowBadge.value = settings.hasActiveFilters
    }

    fun clear() {
        _shouldShowBadge.value = false
    }
}