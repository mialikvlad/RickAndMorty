package com.example.rickmorty.domain.usecase

import com.example.rickmorty.domain.model.RickModel
import com.example.rickmorty.domain.repository.RickRepository

class GetRicksUseCase(private val rickRepository: RickRepository) {

    suspend operator fun invoke(pages: Int): Result<List<RickModel>> {
        return rickRepository.getRicks(pages)
    }
}