package com.example.rickmorty.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.model.Rick
import com.example.rickmorty.retorfit.RickApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RicksViewModel(private val rickApi: RickApi) : ViewModel(){

    private val _ricksFlow = MutableSharedFlow<List<Rick>>()
    val ricksFlow = _ricksFlow.asSharedFlow()

    private var currentPage = 0

    init {
        loadData()
    }

    fun onLoadMore(){
        loadData()
    }

    fun onRefresh(){
        currentPage = 0
        loadData()
    }

    fun loadData() = viewModelScope.launch {
        val since = currentPage
        try {
            val ricks = withContext(Dispatchers.IO) {rickApi.getRick(since).results}
            _ricksFlow.emit(ricks)
            currentPage++
        }catch (e: Throwable){
            _ricksFlow.emit(emptyList())
        }
    }
}