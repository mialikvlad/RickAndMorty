package com.example.rickmorty.data.mapper

import com.example.rickmorty.data.model.Rick
import com.example.rickmorty.data.model.RickInfo
import com.example.rickmorty.domain.model.RickInfoModel
import com.example.rickmorty.domain.model.RickModel

fun Rick.toDomainModel(): RickModel{
    return RickModel(
        id = id,
        name = name,
        image = image
    )
}

fun RickInfo.toDomainModel(): RickInfoModel{
    return RickInfoModel(
        id = id,
        name = name,
        image = image,
        status = status,
        species = species
    )
}