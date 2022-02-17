package com.example.rickmorty.fragment

import android.content.IntentSender
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.R
import com.example.rickmorty.adapter.RickAdapter
import com.example.rickmorty.addHorizontalSpaceDecoration
import com.example.rickmorty.addPaginationScrollListener
import com.example.rickmorty.databinding.FragmentListBinding
import com.example.rickmorty.model.PagingData
import com.example.rickmorty.model.RickList
import com.example.rickmorty.retorfit.RickService
import retrofit2.Call

class ListFragment : BaseFragment<FragmentListBinding>() {
    private val adapter = RickAdapter { rick ->
        findNavController().navigate(
            ListFragmentDirections.toDetails(rick.name)
        )
    }

    private var currentPage = 0
    private var currentRequest: Call<RickList>? = null

    private val isLoading: Boolean
        get() = currentRequest != null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadRicks()

        with(binding) {
            toolbar.menu
                .findItem(R.id.action_search)
                .let { it.actionView as SearchView }
                .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }

                })

            layoutSwiperefresh.setOnRefreshListener {
                adapter.submitList(emptyList())
                currentPage = 0
                loadRicks{
                    layoutSwiperefresh.isRefreshing = false
                }
            }

            val linearLayoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.addHorizontalSpaceDecoration(RECYCLER_ITEM_SPACE)
            recyclerView.addPaginationScrollListener(linearLayoutManager, ITEMS_TO_LOAD, ::loadRicks)
        }
    }

    override fun onViewCleared() {
        //cancel current request if view was destroyed
        currentRequest?.cancel()
    }

    private fun loadRicks(onLoadingFinished: () -> Unit = {}){
        if(isLoading) return

        val since = currentPage
        currentRequest = RickService.rickApi.getRick(since)
            .enqueueRequest(
                onRequestFinished = {
                    currentRequest = null
                    onLoadingFinished()
                },
                result = { ricks ->
                    val newList = adapter.currentList
                        .dropLastWhile { it == PagingData.Loading }
                        .plus(ricks.results.map { PagingData.Content(it) })
                        .plus(PagingData.Loading)
                    adapter.submitList(newList)
                    currentPage++
                }
            )
    }

    companion object {
        private const val RECYCLER_ITEM_SPACE = 50

        private const val ITEMS_TO_LOAD = 15
    }
}