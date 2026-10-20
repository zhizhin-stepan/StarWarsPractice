package com.zhizhin.starwarspractice.domain.model

data class FilterSettings(
    val name: String = "",
    val gender: String = "ALL",
    val minHeight: Int = 0
) {
    val hasActiveFilters: Boolean
        get() = name.isNotBlank() ||
                gender != "ALL" ||
                minHeight > 0
}
