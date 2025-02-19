package com.example.comics.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comics.data.models.ComicsItemModel
import com.example.comics.databinding.ItemListHolderBinding
import com.example.comics.ui.holders.ComicsItemHolder

class ComicsListAdapter(
    private val data: List<ComicsItemModel>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ComicsItemHolder).bind(data = data[position])
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        getItemHolder(parent = parent)

    private fun getItemHolder(parent: ViewGroup) = ComicsItemHolder(
        binding = ItemListHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}
