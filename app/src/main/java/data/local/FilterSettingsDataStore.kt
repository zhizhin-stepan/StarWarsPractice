
package com.zhizhin.starwarspractice.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zhizhin.starwarspractice.domain.model.FilterSettings
import com.zhizhin.starwarspractice.domain.repository.FilterSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.filterSettingsDataStore by preferencesDataStore(
    name = "filter_settings"
)

class FilterSettingsDataStore(
    private val context: Context
) : FilterSettingsRepository {

    override val settings: Flow<FilterSettings> =
        context.filterSettingsDataStore.data.map { preferences ->
            preferences.toFilterSettings()
        }

    override suspend fun saveSettings(settings: FilterSettings) {
        context.filterSettingsDataStore.edit { preferences ->
            preferences[NAME_KEY] = settings.name
            preferences[GENDER_KEY] = settings.gender
            preferences[MIN_HEIGHT_KEY] = settings.minHeight
        }
    }

    override suspend fun clearSettings() {
        context.filterSettingsDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private fun Preferences.toFilterSettings(): FilterSettings {
        return FilterSettings(
            name = this[NAME_KEY] ?: "",
            gender = this[GENDER_KEY] ?: "ALL",
            minHeight = this[MIN_HEIGHT_KEY] ?: 0
        )
    }

    private companion object {
        val NAME_KEY = stringPreferencesKey("filter_name")
        val GENDER_KEY = stringPreferencesKey("filter_gender")
        val MIN_HEIGHT_KEY = intPreferencesKey("filter_min_height")
    }
}
