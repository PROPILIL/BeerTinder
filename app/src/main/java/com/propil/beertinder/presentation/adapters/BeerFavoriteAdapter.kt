package com.propil.beertinder.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.propil.beertinder.R
import com.propil.beertinder.databinding.BeerListFragmentBinding
import com.propil.beertinder.databinding.BeerRecyclerItemBinding
import com.propil.beertinder.domain.model.Beer

class BeerFavoriteAdapter :
    ListAdapter<Beer, BeerFavoriteAdapter.BeerFavoriteViewHolder>(BeerListDiffCallback()) {

    var onBeerClick: ((Beer) -> Unit)? = null


    inner class BeerFavoriteViewHolder(val binding: BeerRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let { it1 -> onBeerClick?.invoke(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerFavoriteViewHolder {
        val binding = BeerRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return BeerFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerFavoriteViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                binding.beerName.text = this?.name
                binding.beerAbv.text = "${this?.abv}"
                binding.beerTagline.text = this?.tagline
                binding.beerImage.load(this?.imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.beer_mug)
                }
            }
        }
    }

}