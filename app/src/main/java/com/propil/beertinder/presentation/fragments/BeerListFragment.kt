package com.propil.beertinder.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.propil.beertinder.R
import com.propil.beertinder.databinding.BeerListFragmentBinding
import com.propil.beertinder.presentation.adapters.BeerListAdapter
import com.propil.beertinder.presentation.adapters.BeerLoadStateAdapter
import kotlinx.coroutines.flow.*

class BeerListFragment : Fragment() {

    private lateinit var viewModel: BeerListViewModel
    private lateinit var beerListAdapter: BeerListAdapter

    private var _binding: BeerListFragmentBinding? = null
    private val binding: BeerListFragmentBinding
        get() = _binding ?: throw RuntimeException("BeerListFragment = null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BeerListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BeerListViewModel::class.java]
//        viewModel.beerList.observe(viewLifecycleOwner) {
//            beerListAdapter.submitList(it)
//        }
        setupRecyclerView()
//        fetchBeers()
    }

//    private fun fetchBeers() {
//        lifecycleScope.launch {
//            viewModel.loadBeerList()
//                .distinctUntilChanged()
//                .collectLatest { beerListAdapter.submitData(it) }
//        }
//    }

    private fun setupRecyclerView() {
        beerListAdapter = BeerListAdapter()
        with(binding) {
            with(beerListAdapter) {
                swipeRefresh.setOnRefreshListener { this.refresh() }
                beerListRecycler.adapter = withLoadStateHeaderAndFooter(
                    header = BeerLoadStateAdapter(this),
                    footer = BeerLoadStateAdapter(this)
                )
            }
            launchOnLifecycleScope {
                viewModel.loadBeerList()
                    .distinctUntilChanged()
                    .collectLatest {
                        beerListAdapter.submitData(it)
                    }

            }
            launchOnLifecycleScope {
                with(beerListAdapter) {
                    loadStateFlow.collectLatest {
                        swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                    }
                }

            }
            launchOnLifecycleScope {
                with(beerListAdapter) {
                    loadStateFlow.distinctUntilChangedBy { it.refresh }
                        .filter { it.refresh is LoadState.NotLoading }
                        .collect { beerListRecycler.scrollToPosition(0) }
                }

            }
        }
        setupClickListener()

    }

    private fun setupClickListener() {
        beerListAdapter.onBeerClick = {
            launchDetails(BeerDetailsFragment.newDetailRemoteInstance(it.id))
        }
    }

    private fun addToFavorite(){
        beerListAdapter.onBeerLongClick {


        }
    }

    private fun launchDetails(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

    }

    private fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): BeerListFragment {
            return BeerListFragment()
        }
    }
}