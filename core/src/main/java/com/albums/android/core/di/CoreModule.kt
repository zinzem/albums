package com.albums.android.core.di

import com.albums.android.core.datasources.jsonplaceholder.JsonPlaceholderDataSource
import com.albums.android.core.datasources.local.LocalDataSource
import com.albums.android.core.network.HttpClient
import com.albums.android.core.repositories.AlbumRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {

    single { HttpClient() }
    single { JsonPlaceholderDataSource(get()) }
    single { LocalDataSource(androidContext()) }

    factory { AlbumRepository(get(), get()) }
}
