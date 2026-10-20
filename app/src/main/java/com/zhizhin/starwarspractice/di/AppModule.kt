package com.zhizhin.starwarspractice.di

import androidx.room.Room
import com.zhizhin.starwarspractice.data.FavoriteRepositoryImpl
import com.zhizhin.starwarspractice.data.local.FavoriteCharacterDao
import com.zhizhin.starwarspractice.data.local.FavoriteDatabase
import com.zhizhin.starwarspractice.data.local.FilterSettingsDataStore
import com.zhizhin.starwarspractice.domain.repository.FavoriteRepository
import com.zhizhin.starwarspractice.domain.repository.FilterSettingsRepository
import com.zhizhin.starwarspractice.presentation.settings.FilterBadgeCache
import com.zhizhin.starwarspractice.presentation.settings.FilterSettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<FilterSettingsRepository> {
        FilterSettingsDataStore(get())
    }

    single {
        Room.databaseBuilder(
            get(),
            FavoriteDatabase::class.java,
            "star_wars_favorites.db"
        ).build()
    }

    single<FavoriteCharacterDao> {
        get<FavoriteDatabase>().favoriteCharacterDao()
    }

    single<FavoriteRepository> {
        FavoriteRepositoryImpl(get())
    }

    single {
        FilterBadgeCache()
    }

    viewModel {
        FilterSettingsViewModel(
            repository = get(),
            badgeCache = get()
        )
    }
}