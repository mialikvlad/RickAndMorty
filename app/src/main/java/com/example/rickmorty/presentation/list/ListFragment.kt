package com.example.rickmorty.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.R
import com.example.rickmorty.presentation.list.adapter.RickAdapter
import com.example.rickmorty.data.retrofit.RickService
import com.example.rickmorty.databinding.FragmentListBinding
import com.example.rickmorty.presentation.extensions.applyInsetsWithAppBar
import com.example.rickmorty.presentation.list.adapter.StateAdapter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.create

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<ListViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ListViewModel(RickService.provideRetrofit().create()) as T
            }
        }
    }

    private val adapter = RickAdapter { rick ->
        findNavController().navigate(
            ListFragmentDirections.toDetails(rick.name)
        )
    }

    private val searchQueryFlow: Flow<String>
        get() = callbackFlow {
            val searchView = binding.toolbar
                .menu
                .findItem(R.id.action_search).actionView as SearchView

            val queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    trySend(newText)
                    return true
                }
            }

            searchView.setOnQueryTextListener(queryTextListener)

            awaitClose {
                searchView.setOnQueryTextListener(null)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = StateAdapter(requireContext()),
                footer = StateAdapter(requireContext())
            )

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel
                    .pagingFilterFlow
                    .collectLatest(adapter::submitData)
            }

            /*viewLifecycleOwner.lifecycleScope.launch {
                adapter
                    .loadStateFlow
                    .distinctUntilChangedBy {
                        it.refresh
                    }
                    .collectLatest {
                        when (val state = it.refresh) {
                            is LoadState.Error -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Error: ${state.error.message}",
                                    Toast.LENGTH_SHORT
                                )
                            }
                            is LoadState.Loading -> {
                                print(true)
                            }
                        }
                    }
            }*/

            appBar.applyInsetsWithAppBar { view, insets ->
                view.updatePadding(left = insets.left, right = insets.right, top = insets.top)
                insets
            }

            recyclerView.applyInsetsWithAppBar { view, insets ->
                view.updatePadding(bottom = insets.bottom)
                insets
            }
        }

        searchQueryFlow
            /*.mapLatest { query ->
                viewModel.searchByName(query)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)*/
            .onEach { query ->
                viewModel.searchByName(query, adapter.snapshot().items)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}