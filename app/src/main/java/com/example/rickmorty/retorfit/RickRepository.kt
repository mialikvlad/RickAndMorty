package com.example.rickmorty.retorfit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RickRepository {
    private val retrofit by lazy(LazyThreadSafetyMode.NONE) { provideRetrofit() }
    val rickApi by lazy(LazyThreadSafetyMode.NONE) {
        retrofit.create<RickApi>()
    }

    private fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}