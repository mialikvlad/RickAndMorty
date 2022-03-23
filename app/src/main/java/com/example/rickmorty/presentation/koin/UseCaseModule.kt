package com.example.rickmorty.presentation.koin

import com.example.rickmorty.domain.usecase.GetRickInfoUseCase
import com.example.rickmorty.domain.usecase.GetRicksUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { GetRickInfoUseCase(get()) }

    factory { GetRicksUseCase(get()) }
}