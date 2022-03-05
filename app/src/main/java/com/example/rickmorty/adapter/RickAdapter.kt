package com.example.rickmorty.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.databinding.ItemCharacterBinding
import com.example.rickmorty.databinding.ItemLoadingBinding
import com.example.rickmorty.model.PagingData
import com.example.rickmorty.model.Rick

class RickAdapter(
    private val onRickClicked: (Rick) -> Unit
) : ListAdapter<PagingData<Rick>, RecyclerView.ViewHolder>(DIFF_CALLBACK){

    override fun getItemViewType(position: Int):Int{
        return when (getItem(position)) {
            is PagingData.Content -> TYPE_USER
            PagingData.Loading -> TYPE_LOADING
        }
    }

    companion object{
        private const val TYPE_USER = 1
        private const val TYPE_LOADING = 2

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PagingData<Rick>>(){
            override fun areItemsTheSame(
                oldItem: PagingData<Rick>,
                newItem: PagingData<Rick>
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PagingData<Rick>,
                newItem: PagingData<Rick>
            ): Boolean {
                val oldUser = oldItem as? PagingData.Content
                val newUser = newItem as? PagingData.Content
                return oldUser?.data == newUser?.data
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType){
            TYPE_USER -> {
                RickViewHolder(
                   binding = ItemCharacterBinding.inflate(layoutInflater, parent, false),
                   onRickClicked = onRickClicked
                )
            }
            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> error("Unsupported viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val rick = (getItem(position) as? PagingData.Content)?.data?: return
        (holder as? RickViewHolder)?.bind(rick)
    }
}