package com.example.comics.ui.holders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.comics.data.models.ComicsItemModel
import com.example.comics.databinding.ItemListHolderBinding

class ComicsItemHolder(
    private val binding: ItemListHolderBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: ComicsItemModel) {
        binding.apply {
            actionTitle.text = data.title
            actionSubTitle.text = data.subtitle
            Glide.with(binding.root)
                .load(data.image)
                .centerCrop()
                .into(actionImage)
        }
    }
}