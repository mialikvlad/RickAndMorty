package com.example.rickmorty.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.rickmorty.data.model.Rick
import com.example.rickmorty.data.retrofit.RickApi
import com.example.rickmorty.presentation.paging.RickPagingSource
import kotlinx.coroutines.flow.*

class ListViewModel(private val rickApi: RickApi) : ViewModel() {

    private var listData: List<Rick>? = null

    private val queryFlow = MutableStateFlow("")
    private var oldQuery = ""
    /*private val queryFlow = MutableSharedFlow<String>(replay = 1, extraBufferCapacity = 0)*/

    val pagingFilterFlow = queryFlow.debounce(1000).flatMapLatest { query ->
        pagingFlow(query)
    }

    fun searchByName(name: String, listRicks: List<Rick>?) {
        listData = if(name.isEmpty()) null else listRicks
        if (oldQuery != "" || name != "") {
            queryFlow.tryEmit(name)
        }
        oldQuery = name
    }

    private fun pagingFlow(query: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            initialLoadSize = 20,
            prefetchDistance = 15,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { RickPagingSource(rickApi, query, listData) }
    ).flow
        .cachedIn(viewModelScope)
}
