package com.zhizhin.starwarspractice.presentation

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.zhizhin.starwarspractice.domain.model.Character
import com.zhizhin.starwarspractice.presentation.characters.CharactersViewModel

@Composable
fun CharactersListScreen(
    viewModel: CharactersViewModel,
    onCharacterClick: (Int) -> Unit,
    favoriteIds: Set<Int> = emptySet(),
    onFavoriteClick: (Character) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    var searchText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Персонажи Star Wars",
            style = MaterialTheme.typography.headlineSmall
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.weight(1f),
                label = { Text("Имя персонажа") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchCharacters(searchText)
                    }
                )
            )

            Button(
                onClick = {
                    viewModel.searchCharacters(searchText)
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Найти")
            }
        }

        Spacer(modifier = Modifier.padding(4.dp))

        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error
                )

                Button(onClick = viewModel::retry) {
                    Text("Повторить")
                }
            }

            state.characters.isEmpty() -> {
                Text("Персонажи не найдены.")
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.characters,
                        key = { it.id }
                    ) { character ->
                        CharacterCard(
                            character = character,
                            isFavorite = character.id in favoriteIds,
                            onClick = {
                                onCharacterClick(character.id)
                            },
                            onFavoriteClick = {
                                onFavoriteClick(character)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterCard(
    character: Character,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onClick)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text("Рост: ${character.height} см")
                Text("Пол: ${character.gender}")
            }

            OutlinedButton(onClick = onFavoriteClick) {
                Text(if (isFavorite) "★" else "☆")
            }
        }
    }
}