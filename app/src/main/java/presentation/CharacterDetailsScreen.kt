package com.zhizhin.starwarspractice.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.zhizhin.starwarspractice.domain.model.Character

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    character: Character,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Информация о персонаже")
                },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            val (
                name,
                basicInfo,
                appearance,
                origin
            ) = createRefs()

            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(basicInfo) {
                        top.linkTo(name.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Основная информация",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text("Рост: ${character.height} см")
                    Text("Вес: ${character.mass} кг")
                    Text("Год рождения: ${character.birthYear}")
                    Text("Пол: ${character.gender}")
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(appearance) {
                        top.linkTo(basicInfo.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Внешность",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text("Цвет волос: ${character.hairColor}")
                    Text("Цвет кожи: ${character.skinColor}")
                    Text("Цвет глаз: ${character.eyeColor}")
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(origin) {
                        top.linkTo(appearance.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Происхождение",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text("Родная планета: ${character.homeworld}")
                }
            }
        }
    }
}