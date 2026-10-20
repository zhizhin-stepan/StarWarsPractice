
package com.zhizhin.starwarspractice.domain.repository

import com.zhizhin.starwarspractice.domain.model.FilterSettings
import kotlinx.coroutines.flow.Flow

interface FilterSettingsRepository {

    val settings: Flow<FilterSettings>

    suspend fun saveSettings(settings: FilterSettings)

    suspend fun clearSettings()
}
