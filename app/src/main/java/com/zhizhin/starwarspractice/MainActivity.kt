package com.zhizhin.starwarspractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import navigation.AppNavigation
import com.zhizhin.starwarspractice.ui.theme.StarWarsPracticeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            StarWarsPracticeTheme {
                AppNavigation()
            }
        }
    }
}