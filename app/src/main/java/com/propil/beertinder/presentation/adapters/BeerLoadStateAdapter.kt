package com.propil.beertinder.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.propil.beertinder.R
import com.propil.beertinder.databinding.DefaultLoadStateBinding
import com.propil.beertinder.databinding.LoadingLayoutItemBinding

class BeerLoadStateAdapter(
    private val adapter: BeerListAdapter
) : LoadStateAdapter<BeerLoadStateAdapter.NetworkStateItemViewHolder>() {

    class NetworkStateItemViewHolder(
        private val binding: LoadingLayoutItemBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

//        init {
//            binding.retryButton.setOnClickListener { retryCallback() }
//        }

        fun bind(loadState: LoadState) {
            val progressBar = binding.progressBar

            when (loadState) {
                is LoadState.Loading -> {
                    progressBar.isVisible = true
                }
                is LoadState.NotLoading -> {
                    progressBar.isVisible = false
                }
                is LoadState.Error -> {
                    progressBar.isVisible = true
                }
            }
        }

//        fun bind(loadState: LoadState) = with(binding) {
//            progressBar.isVisible = loadState is LoadState.Loading
//            retryButton.isVisible = loadState is LoadState.Error
//            errorMsg.isVisible =
//                !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
//            errorMsg.text = "Something went wrong"
//            Log.e("loadState", "$loadState")
//        }

    }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateItemViewHolder(
            LoadingLayoutItemBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.default_load_state, parent, false)
            )
        ) { adapter.retry() }
}