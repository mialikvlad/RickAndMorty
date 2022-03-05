package com.example.rickmorty.retorfit

import com.example.rickmorty.model.RickList
import com.example.rickmorty.model.RickListInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RickApi {
    @GET("character")
    fun getRick(
        @Query("page") page: Int
    ): Call<RickList>

    @GET("character")
    fun getRickInfo(
        @Query("name") name: String
    ): Call<RickListInfo>
}