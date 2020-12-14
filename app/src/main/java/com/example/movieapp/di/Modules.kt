package com.example.movieapp.di

import com.example.movieapp.database.getDatabase
import com.example.movieapp.overview.OverviewViewModel
import com.example.movieapp.repositories.MoviesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { MoviesRepository(getDatabase(get())) }

    viewModel { OverviewViewModel(get()) }

}