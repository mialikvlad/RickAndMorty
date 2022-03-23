package com.example.rickmorty.presentation.koin

import com.example.rickmorty.presentation.location.LocationService
import org.koin.dsl.module

val locationModule = module {
    single { LocationService(get()) }
}