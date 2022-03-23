package com.example.rickmorty.presentation

import android.app.Application
import com.example.rickmorty.presentation.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RickApp : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RickApp)
            modules(
                useCaseModule,
                viewModelModule,
                repositoryModule,
                networkModule,
                sharedPrefsModule,
                locationModule
            )
        }
    }
}