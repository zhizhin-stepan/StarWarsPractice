package com.zhizhin.starwarspractice.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun CharacterImage(
    characterId: Int,
    characterName: String,
    modifier: Modifier = Modifier
) {
    val imageUrl =
        "https://starwars-visualguide.com/assets/img/characters/$characterId.jpg"

    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "Изображение персонажа $characterName",
        modifier = modifier,
        contentScale = ContentScale.Crop
    ) {
        when (painter.state) {
            is coil.compose.AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Загрузка...")
                }
            }

            is coil.compose.AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Нет фото")
                }
            }

            else -> {
                SubcomposeAsyncImageContent()
            }
        }
    }
}