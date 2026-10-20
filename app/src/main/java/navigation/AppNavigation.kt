package navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zhizhin.starwarspractice.domain.model.Character
import com.zhizhin.starwarspractice.domain.repository.FavoriteRepository
import com.zhizhin.starwarspractice.domain.repository.FilterSettingsRepository
import com.zhizhin.starwarspractice.presentation.CharacterDetailsScreen
import com.zhizhin.starwarspractice.presentation.CharactersListScreen
import com.zhizhin.starwarspractice.presentation.favorites.FavoritesScreen
import com.zhizhin.starwarspractice.presentation.favorites.FavoritesViewModel
import com.zhizhin.starwarspractice.presentation.favorites.FavoritesViewModelFactory
import com.zhizhin.starwarspractice.presentation.characters.CharactersViewModel
import com.zhizhin.starwarspractice.presentation.characters.CharactersViewModelFactory
import com.zhizhin.starwarspractice.presentation.settings.FilterBadgeCache
import com.zhizhin.starwarspractice.presentation.settings.FilterSettingsScreen
import org.koin.java.KoinJavaComponent

private const val CHARACTERS_ROUTE = "characters"
private const val FAVORITES_ROUTE = "favorites"
private const val SETTINGS_ROUTE = "settings"
private const val DETAILS_ROUTE = "details/{characterId}"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val charactersViewModel: CharactersViewModel = viewModel(
        factory = CharactersViewModelFactory()
    )

    val charactersState by charactersViewModel.uiState.collectAsState()

    val favoriteRepository: FavoriteRepository = remember {
        KoinJavaComponent.get(FavoriteRepository::class.java)
    }

    val favoritesViewModel: FavoritesViewModel = viewModel(
        factory = remember(favoriteRepository) {
            FavoritesViewModelFactory(favoriteRepository)
        }
    )

    val favoritesState by favoritesViewModel.uiState.collectAsState()
    val favoriteIds by favoritesViewModel.favoriteIds.collectAsState()

    val settingsRepository: FilterSettingsRepository = remember {
        KoinJavaComponent.get(FilterSettingsRepository::class.java)
    }

    val filterSettings by settingsRepository.settings.collectAsState(
        initial = null
    )

    val badgeCache: FilterBadgeCache = remember {
        KoinJavaComponent.get(FilterBadgeCache::class.java)
    }

    val showFilterBadge by badgeCache.shouldShowBadge.collectAsState()

    LaunchedEffect(filterSettings) {
        filterSettings?.let { settings ->
            charactersViewModel.setFilterSettings(settings)
            badgeCache.update(settings)
        }
    }

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
                    icon = {
                        Text("★")
                    },
                    label = {
                        Text("Персонажи")
                    }
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
                    icon = {
                        Text("☆")
                    },
                    label = {
                        Text("Избранное")
                    }
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
                    icon = {
                        BadgedBox(
                            badge = {
                                if (showFilterBadge) {
                                    Badge()
                                }
                            }
                        ) {
                            Text("⚙")
                        }
                    },
                    label = {
                        Text("Настройки")
                    }
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
                    viewModel = charactersViewModel,
                    favoriteIds = favoriteIds,
                    onFavoriteClick = favoritesViewModel::toggleFavorite,
                    onCharacterClick = { characterId ->
                        navController.navigate("details/$characterId")
                    }
                )
            }

            composable(FAVORITES_ROUTE) {
                FavoritesScreen(
                    viewModel = favoritesViewModel,
                    onCharacterClick = { characterId ->
                        navController.navigate("details/$characterId")
                    }
                )
            }

            composable(SETTINGS_ROUTE) {
                FilterSettingsScreen(
                    onDone = {
                        navController.navigate(CHARACTERS_ROUTE) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(DETAILS_ROUTE) { entry ->
                val characterId = entry.arguments
                    ?.getString("characterId")
                    ?.toIntOrNull()

                val character: Character? =
                    charactersState.characters.firstOrNull {
                        it.id == characterId
                    } ?: favoritesState.characters.firstOrNull {
                        it.id == characterId
                    }

                if (character != null) {
                    CharacterDetailsScreen(
                        character = character,
                        isFavorite = character.id in favoriteIds,
                        onFavoriteClick = {
                            favoritesViewModel.toggleFavorite(character)
                        },
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                } else if (
                    charactersState.isLoading ||
                    favoritesState.isLoading
                ) {
                    Text("Загрузка информации о персонаже...")
                } else {
                    Text("Персонаж не найден")
                }
            }
        }
    }
}