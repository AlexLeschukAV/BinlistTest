package com.example.binlisttest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.binlisttest.databinding.ItemBinding
import com.example.binlisttest.domain.model.BinlistData

class ArchiveAdapter : ListAdapter<BinlistData, ArchiveAdapter.ArchiveViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArchiveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {
        val card = getItem(position)
        holder.bind(card)
    }

    class ArchiveViewHolder(
        private val binding: ItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BinlistData) = with(binding) {
            cardNumber.text = data.request
            country.text = data.country?.name
            typeCard.text = data.scheme
            phoneCard.text = data.bank?.phone
            urlCard.text = data.bank?.url
            val lat_long = "lat: ${data.country?.latitude}   long:${data.country?.longitude}"
            coordinateCard.text = lat_long
        }

    }
}

private class DiffCallback : DiffUtil.ItemCallback<BinlistData>() {
    override fun areItemsTheSame(oldItem: BinlistData, newItem: BinlistData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: BinlistData, newItem: BinlistData): Boolean {
        return true
    }
}