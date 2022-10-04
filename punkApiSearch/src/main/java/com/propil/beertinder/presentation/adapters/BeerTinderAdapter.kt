package com.propil.beertinder.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.propil.beertinder.databinding.TinderRecyclerItemBinding
import com.propil.beertinder.domain.model.Beer
import com.propil.beertinder.presentation.utils.loadWithCoil

class BeerTinderAdapter :
    ListAdapter<Beer, BeerTinderAdapter.BeerTinderViewHolder>(BeerListDiffCallback()) {

    inner class BeerTinderViewHolder(val binding: TinderRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerTinderViewHolder {
        val binding = TinderRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return BeerTinderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerTinderViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                binding.beerName.text = this?.name
                binding.beerAbv.text = "${this?.abv}"
                binding.beerTagline.text = this?.tagline
                binding.beerImage.loadWithCoil(this?.imageUrl)
            }
        }
    }


}