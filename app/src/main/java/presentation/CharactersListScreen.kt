package com.zhizhin.starwarspractice.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhizhin.starwarspractice.domain.model.Character
import com.zhizhin.starwarspractice.presentation.characters.CharactersViewModel
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersListScreen(
    onCharacterClick: (Int) -> Unit,
    viewModel: CharactersViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Персонажи Star Wars")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Загрузка персонажей...",
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            }

            if (uiState.error != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = uiState.error.orEmpty(),
                        color = MaterialTheme.colorScheme.error
                    )

                    Button(
                        onClick = { viewModel.retry() },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Повторить")
                    }
                }
            }

            if (!uiState.isLoading &&
                uiState.error == null &&
                uiState.characters.isEmpty()
            ) {
                Text(
                    text = "Персонажи не найдены",
                    modifier = Modifier.padding(16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = uiState.characters,
                    key = { it.id }
                ) { character ->
                    CharacterItem(
                        character = character,
                        onClick = {
                            onCharacterClick(character.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterItem(
    character: Character,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CharacterImage(
                characterId = character.id,
                characterName = character.name,
                modifier = Modifier
                    .width(100.dp)
                    .height(120.dp)
            )

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge
                )

                Text("Рост: ${character.height} см")
                Text("Вес: ${character.mass} кг")
                Text("Год рождения: ${character.birthYear}")
            }
        }
    }
}