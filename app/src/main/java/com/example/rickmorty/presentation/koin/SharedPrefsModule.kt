package com.example.rickmorty.presentation.koin

import com.example.rickmorty.presentation.manager.SharedPrefersManager
import org.koin.dsl.module

val sharedPrefsModule = module {
    single { SharedPrefersManager(get()) }
}