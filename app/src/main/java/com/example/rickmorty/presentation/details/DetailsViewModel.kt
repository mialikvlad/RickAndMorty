package com.example.rickmorty.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.domain.model.RickInfoModel
import com.example.rickmorty.domain.usecase.GetRickInfoUseCase
import com.example.rickmorty.presentation.model.LceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

class DetailsViewModel(
    private val getRickInfoUseCase: GetRickInfoUseCase,
    private val name: String
) : ViewModel() {

    val rickDetailsFlow: Flow<LceState<RickInfoModel>> = flow {
        val lseState = getRickInfoUseCase(name)
            .fold(
                onSuccess = {
                    LceState.Content(it)
                },
                onFailure = {
                    LceState.Error(it)
                }
            )
        emit(lseState)
    }.shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)
}