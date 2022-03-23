package com.example.rickmorty.presentation.list.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.rickmorty.databinding.ItemCharacterBinding
import com.example.rickmorty.data.model.Rick

/*class RickAdapter(
    private val onRickClicked: (Rick) -> Unit
) : ListAdapter<PagingData<Rick>, RecyclerView.ViewHolder>(DIFF_CALLBACK){

    override fun getItemViewType(position: Int):Int{
        return when (getItem(position)) {
            is PagingData.Content -> TYPE_CHARACTER
            PagingData.Loading -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType){
            TYPE_CHARACTER -> {
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

    companion object{
        private const val TYPE_CHARACTER = 1
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
}*/

class RickAdapter(
    private val onRickCliсked: (Rick) -> Unit
) : PagingDataAdapter<Rick, RickViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RickViewHolder {
        return RickViewHolder(
            binding = ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onRickClicked = onRickCliсked
        )
    }

    override fun onBindViewHolder(holder: RickViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Rick>() {
            override fun areItemsTheSame(oldItem: Rick, newItem: Rick): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Rick, newItem: Rick): Boolean {
                return oldItem == newItem
            }
        }
    }

}