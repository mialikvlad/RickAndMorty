package com.example.rickmorty.presentation.koin

import com.example.rickmorty.data.repository.RickRepositoryImpl
import com.example.rickmorty.domain.repository.RickRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<RickRepository> { RickRepositoryImpl(get()) }
}