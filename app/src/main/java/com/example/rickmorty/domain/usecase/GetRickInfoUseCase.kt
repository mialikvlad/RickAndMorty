package com.example.rickmorty.domain.usecase

import com.example.rickmorty.domain.model.RickInfoModel
import com.example.rickmorty.domain.repository.RickRepository

class GetRickInfoUseCase(private val rickRepository: RickRepository) {

    suspend operator fun invoke(name: String): Result<RickInfoModel> {
        return rickRepository.getRickInfo(name)
    }
}