package com.albums.android.di

import com.albums.android.views.viewmodels.MainActivityVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainActivityVM(get()) }
}
