package com.example.movieapp.di

import com.example.movieapp.database.getDatabase
import com.example.movieapp.overview.OverviewViewModel
import com.example.movieapp.repositories.MoviesRepository
import com.example.movieapp.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { OverviewViewModel(get()) }

    viewModel { SearchViewModel(get()) }

    single { MoviesRepository(getDatabase(get())) }

}