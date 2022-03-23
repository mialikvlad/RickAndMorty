package com.example.rickmorty.domain.repository

import com.example.rickmorty.domain.model.RickInfoModel
import com.example.rickmorty.domain.model.RickModel

interface RickRepository {

    suspend fun getRicks(pages: Int): Result<List<RickModel>>

    suspend fun getRickInfo(name: String): Result<RickInfoModel>
}