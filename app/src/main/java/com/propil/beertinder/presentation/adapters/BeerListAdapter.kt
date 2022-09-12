package com.propil.beertinder.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.propil.beertinder.databinding.BeerRecyclerItemBinding
import com.propil.beertinder.domain.model.Beer
import com.propil.beertinder.presentation.utils.ImageLoader

class BeerListAdapter :
    PagingDataAdapter<Beer, BeerListAdapter.BeerListViewHolder>(BeerListDiffCallback()) {

    var onBeerClick: ((Beer) -> Unit)? = null
    var onBeerLongClick: ((Beer) -> Unit)? = null


    inner class BeerListViewHolder(val binding: BeerRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let { it -> onBeerClick?.invoke(it) }
            }
            binding.root.setOnLongClickListener {
                getItem(absoluteAdapterPosition)?.let { it -> onBeerClick?.invoke(it) }
                true
            }
        }

        fun bind(beer: Beer?) {
            with(binding) {
                beerName.text = beer?.name
                beerAbv.text = "${beer?.abv}"
                beerTagline.text = beer?.tagline
                ImageLoader.loadImageWithCoil(binding.beerImage, beer?.imageUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerListViewHolder {
        val binding = BeerRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BeerListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}