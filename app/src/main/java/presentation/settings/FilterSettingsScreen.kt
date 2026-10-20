package com.zhizhin.starwarspractice.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun FilterSettingsScreen(
    onDone: () -> Unit
) {
    val viewModel: FilterSettingsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(12.dp))
            Text("Загрузка настроек...")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Настройки фильтрации",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Укажи параметры, по которым нужно отбирать персонажей.",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = uiState.name,
            onValueChange = viewModel::onNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Имя персонажа") },
            placeholder = { Text("Например, Luke") },
            singleLine = true
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Пол",
                style = MaterialTheme.typography.titleMedium
            )

            GenderOption(
                label = "Любой",
                value = "ALL",
                selectedValue = uiState.gender,
                onSelect = viewModel::onGenderChange
            )

            GenderOption(
                label = "Мужской",
                value = "male",
                selectedValue = uiState.gender,
                onSelect = viewModel::onGenderChange
            )

            GenderOption(
                label = "Женский",
                value = "female",
                selectedValue = uiState.gender,
                onSelect = viewModel::onGenderChange
            )

            GenderOption(
                label = "Другой / неизвестен",
                value = "other",
                selectedValue = uiState.gender,
                onSelect = viewModel::onGenderChange
            )
        }

        OutlinedTextField(
            value = uiState.minHeight,
            onValueChange = viewModel::onMinHeightChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Минимальный рост (см)") },
            placeholder = { Text("0 — без ограничения") },
            supportingText = {
                Text("Допустимый диапазон: 0–300 см")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )

        if (uiState.hasActiveFilters) {
            Text(
                text = "Фильтры активны",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            Text(
                text = "Используются значения по умолчанию",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        uiState.error?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (uiState.saved) {
            Text(
                text = "Настройки сохранены",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    viewModel.saveSettings(onDone)
                },
                modifier = Modifier.weight(1f),
                enabled = !uiState.isSaving
            ) {
                Text(
                    if (uiState.isSaving) "Сохранение..."
                    else "Готово"
                )
            }

            OutlinedButton(
                onClick = viewModel::resetSettings,
                modifier = Modifier.weight(1f),
                enabled = !uiState.isSaving
            ) {
                Text("Сбросить")
            }
        }
    }
}

@Composable
private fun GenderOption(
    label: String,
    value: String,
    selectedValue: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedValue == value,
            onClick = { onSelect(value) }
        )

        Text(
            text = label,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
    }
}