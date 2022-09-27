package com.propil.beertinder.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.propil.beertinder.domain.model.Beer

class BeerListDiffCallback: DiffUtil.ItemCallback<Beer>() {
    override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Beer, newItem: Beer): Any? {
        return super.getChangePayload(oldItem, newItem)
    }
}