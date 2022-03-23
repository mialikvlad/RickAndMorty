package com.example.rickmorty.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickmorty.data.model.Rick
import com.example.rickmorty.data.retrofit.RickApi

class RickPagingSource(
    private val rickApi: RickApi,
    private val query: String,
    private val listRicks: List<Rick>?
) : PagingSource<Int, Rick>() {

    override fun getRefreshKey(state: PagingState<Int, Rick>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Rick> {
        return try {
            val nextPage = params.key ?: FIRST_PAGE
            val loadSize = params.loadSize.coerceAtLeast(MAX_PAGE_SIZE)
            /*var ricks : MutableList<Rick> = mutableListOf()
            if(query != "") {
                for (i in 0..nextPage) {
                    ricks += rickApi.getRick(i).results
                }
                ricks = ricks
                    .filter {
                        it.name.contains(query, true)
                    }.toMutableList()
                if (ricks.size > 20)
                    ricks.subList(0,20)
            }
            else {
                ricks = rickApi.getRick(nextPage).results.toMutableList()
            }*/
            var ricks = listRicks ?: rickApi.getRick(nextPage).results
            if (query != ""){
                ricks = ricks.filter { it.name.contains(query, true) }
            }

            LoadResult.Page(
                data = ricks,
                prevKey = null,
                nextKey = if (ricks.size == loadSize) nextPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val MAX_PAGE_SIZE = 20
    }
}