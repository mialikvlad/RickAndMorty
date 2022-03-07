package com.example.rickmorty.retorfit

import com.example.rickmorty.model.RickList
import com.example.rickmorty.model.RickListInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface RickApi {
    @GET("character")
    suspend fun getRick(
        @Query("page") page: Int
    ): RickList

    @GET("character")
    suspend fun getRickInfo(
        @Query("name") name: String
    ): RickListInfo
}