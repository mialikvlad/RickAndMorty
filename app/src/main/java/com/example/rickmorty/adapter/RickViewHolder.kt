package com.example.rickmorty.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.example.rickmorty.databinding.ItemCharacterBinding
import com.example.rickmorty.model.Rick

class RickViewHolder(
    private val binding: ItemCharacterBinding,
    private val onRickClicked: (Rick) -> Unit
) : RecyclerView.ViewHolder(binding.root){
    fun bind(character: Rick){
        with(binding){
            image.load(character.image){
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
            textName.text = character.name
            root.setOnClickListener{
                onRickClicked(character)
            }
        }
    }
}