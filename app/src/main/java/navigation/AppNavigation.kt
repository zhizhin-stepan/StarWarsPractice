package navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zhizhin.starwarspractice.presentation.CharacterDetailsScreen
import com.zhizhin.starwarspractice.presentation.CharactersListScreen
import com.zhizhin.starwarspractice.presentation.PlaceholderScreen
import com.zhizhin.starwarspractice.presentation.characters.CharactersViewModel
import com.zhizhin.starwarspractice.presentation.characters.CharactersViewModelFactory

private const val CHARACTERS_ROUTE = "characters"
private const val FAVORITES_ROUTE = "favorites"
private const val SETTINGS_ROUTE = "settings"
private const val DETAILS_ROUTE = "details/{characterId}"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val viewModel: CharactersViewModel = viewModel(
        factory = CharactersViewModelFactory()
    )

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == CHARACTERS_ROUTE,
                    onClick = {
                        navController.navigate(CHARACTERS_ROUTE) {
                            popUpTo(
                                navController.graph.findStartDestination().id
                            ) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Text("★") },
                    label = { Text("Персонажи") }
                )

                NavigationBarItem(
                    selected = currentRoute == FAVORITES_ROUTE,
                    onClick = {
                        navController.navigate(FAVORITES_ROUTE) {
                            popUpTo(
                                navController.graph.findStartDestination().id
                            ) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Text("☆") },
                    label = { Text("Избранное") }
                )

                NavigationBarItem(
                    selected = currentRoute == SETTINGS_ROUTE,
                    onClick = {
                        navController.navigate(SETTINGS_ROUTE) {
                            popUpTo(
                                navController.graph.findStartDestination().id
                            ) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Text("⚙") },
                    label = { Text("Настройки") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CHARACTERS_ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(CHARACTERS_ROUTE) {
                CharactersListScreen(
                    viewModel = viewModel,
                    onCharacterClick = { characterId ->
                        navController.navigate("details/$characterId")
                    }
                )
            }

            composable(FAVORITES_ROUTE) {
                PlaceholderScreen(
                    title = "Избранное",
                    description = "Раздел будет реализован позже"
                )
            }

            composable(SETTINGS_ROUTE) {
                PlaceholderScreen(
                    title = "Настройки",
                    description = "Раздел будет реализован позже"
                )
            }

            composable(DETAILS_ROUTE) { entry ->
                val characterId = entry.arguments
                    ?.getString("characterId")
                    ?.toIntOrNull()

                val character = uiState.characters.firstOrNull {
                    it.id == characterId
                }

                if (character != null) {
                    CharacterDetailsScreen(
                        character = character,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                } else if (uiState.isLoading) {
                    Text("Загрузка информации о персонаже...")
                } else {
                    Text("Персонаж не найден")
                }
            }
        }
    }
}