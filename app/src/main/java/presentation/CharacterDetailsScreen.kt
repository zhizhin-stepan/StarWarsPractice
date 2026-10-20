package com.zhizhin.starwarspractice.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhizhin.starwarspractice.domain.model.Character

@Composable
fun CharacterDetailsScreen(
    character: Character,
    onBackClick: () -> Unit,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium
        )

        Text("ID: ${character.id}")
        Text("Рост: ${character.height} см")
        Text("Масса: ${character.mass}")
        Text("Цвет волос: ${character.hairColor}")
        Text("Цвет кожи: ${character.skinColor}")
        Text("Цвет глаз: ${character.eyeColor}")
        Text("Год рождения: ${character.birthYear}")
        Text("Пол: ${character.gender}")
        Text("Родная планета: ${character.homeworld}")

        OutlinedButton(
            onClick = onFavoriteClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (isFavorite) {
                    "★ Удалить из избранного"
                } else {
                    "☆ Добавить в избранное"
                }
            )
        }

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}