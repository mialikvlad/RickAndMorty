package com.example.rickmorty.provider

import com.example.rickmorty.retorfit.RickApi
import com.example.rickmorty.retorfit.RickRepository

object ServiceProvider {

    private val rickRepository by lazy { RickRepository }

    fun provideRickApi() : RickApi {
        return rickRepository.rickApi
    }
}