package com.example.rickmorty.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.model.RickListInfo
import com.example.rickmorty.retorfit.RickApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

class DetailsViewModel(
    private val rickApi: RickApi,
    private val name: String
) : ViewModel() {

    val rickDetailsFlow: Flow<RickListInfo> = flow {
        emit(rickApi.getRickInfo(name))
    }.shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)
}