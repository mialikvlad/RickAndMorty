package com.example.rickmorty.presentation.koin

import com.example.rickmorty.presentation.details.DetailsViewModel
import com.example.rickmorty.presentation.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ListViewModel(get()) }

    viewModel { (name: String) -> DetailsViewModel(get(), name) }
}