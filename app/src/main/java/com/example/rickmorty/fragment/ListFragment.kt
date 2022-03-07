package com.example.rickmorty.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.adapter.RickAdapter
import com.example.rickmorty.addHorizontalSpaceDecoration
import com.example.rickmorty.addPaginationScrollListener
import com.example.rickmorty.databinding.FragmentListBinding
import com.example.rickmorty.model.PagingData
import com.example.rickmorty.provider.ServiceProvider
import com.example.rickmorty.viewModel.RicksViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListFragment : BaseFragment<FragmentListBinding>() {

    private val viewModel by viewModels<RicksViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return RicksViewModel(ServiceProvider.provideRickApi()) as T
            }
        }
    }

    private val adapter = RickAdapter { rick ->
        findNavController().navigate(
            ListFragmentDirections.toDetails(rick.name)
        )
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel
            .ricksFlow
            .onEach { ricks ->
                val newList = adapter.currentList
                    .dropLastWhile { it == PagingData.Loading }
                    .plus(ricks.map { PagingData.Content(it) })
                    .plus(PagingData.Loading)
                adapter.submitList(newList)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        with(binding) {
            layoutSwiperefresh.setOnRefreshListener {
                adapter.submitList(emptyList())
                viewModel.onRefresh()
            }

            val linearLayoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.addHorizontalSpaceDecoration(RECYCLER_ITEM_SPACE)
            recyclerView.addPaginationScrollListener(
                linearLayoutManager,
                ITEMS_TO_LOAD,
                viewModel::onLoadMore
            )
        }
    }

    companion object {
        private const val RECYCLER_ITEM_SPACE = 50

        private const val ITEMS_TO_LOAD = 20
    }
}