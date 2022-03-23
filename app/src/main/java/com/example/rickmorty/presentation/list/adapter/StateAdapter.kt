package com.example.rickmorty.presentation.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.databinding.ItemLoadingBinding

class StateAdapter(private val context: Context) : LoadStateAdapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return when (loadState){
            is LoadState.Loading -> LoadingItemViewHolder(
                binding = ItemLoadingBinding.inflate(inflater, parent, false)
            )
            is LoadState.NotLoading -> error("Not supported")
            is LoadState.Error -> error("Not supported")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {

    }

}