package com.zhizhin.starwarspractice.presentation.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhizhin.starwarspractice.domain.model.Character

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onCharacterClick: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Избранные персонажи",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.padding(4.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.characters.isEmpty()) {
            Text("В избранном пока нет персонажей.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = state.characters,
                    key = { it.id }
                ) { character ->
                    FavoriteCharacterCard(
                        character = character,
                        onClick = {
                            onCharacterClick(character.id)
                        },
                        onRemove = {
                            viewModel.removeFavorite(character.id)
                        }
                    )
                }
            }
        }

        state.error?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun FavoriteCharacterCard(
    character: Character,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text("Рост: ${character.height} см")
                Text("Пол: ${character.gender}")
            }

            OutlinedButton(onClick = onRemove) {
                Text("Удалить")
            }
        }
    }
}