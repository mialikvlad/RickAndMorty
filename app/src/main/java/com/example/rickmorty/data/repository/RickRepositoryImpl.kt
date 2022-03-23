package com.example.rickmorty.data.repository

import com.example.rickmorty.data.mapper.toDomainModel
import com.example.rickmorty.data.retrofit.RickApi
import com.example.rickmorty.domain.model.RickInfoModel
import com.example.rickmorty.domain.model.RickModel
import com.example.rickmorty.domain.repository.RickRepository

class RickRepositoryImpl(private val rickApi: RickApi) : RickRepository {

    override suspend fun getRicks(pages: Int): Result<List<RickModel>> = runCatching {
        rickApi.getRick(pages)
    }.map { ricks -> ricks.results.map { it.toDomainModel() } }

    override suspend fun getRickInfo(name: String): Result<RickInfoModel> = runCatching {
        rickApi.getRickInfo(name)
    }.map { it.results[0].toDomainModel() }
}