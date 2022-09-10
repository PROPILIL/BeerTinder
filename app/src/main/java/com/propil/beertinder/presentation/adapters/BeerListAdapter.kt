package com.propil.beertinder.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.propil.beertinder.R
import com.propil.beertinder.databinding.BeerListFragmentBinding
import com.propil.beertinder.databinding.BeerRecyclerItemBinding
import com.propil.beertinder.domain.model.Beer

class BeerListAdapter :
    PagingDataAdapter<Beer, BeerListAdapter.BeerListViewHolder>(BeerListDiffCallback()) {

    var onBeerClick: ((Beer) -> Unit)? = null


    inner class BeerListViewHolder(val binding: BeerRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let { it -> onBeerClick?.invoke(it) }
            }
        }

        fun bind(beer: Beer?) {
            with(binding) {
                beerName.text = beer?.name
                beerAbv.text = "${beer?.abv}"
                beerTagline.text = beer?.tagline
                beerImage.load(beer?.imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.beer_mug)
                }
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