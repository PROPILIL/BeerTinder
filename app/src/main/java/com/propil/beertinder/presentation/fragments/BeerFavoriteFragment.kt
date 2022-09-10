package com.propil.beertinder.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.propil.beertinder.R
import com.propil.beertinder.databinding.BeerListFragmentBinding
import com.propil.beertinder.presentation.adapters.BeerFavoriteAdapter
import com.propil.beertinder.presentation.adapters.BeerListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class BeerFavoriteFragment : Fragment() {

    private lateinit var viewModel: BeerFavoritesViewModel
    private lateinit var beerFavoritesAdapter: BeerFavoriteAdapter

    private var _binding: BeerListFragmentBinding? = null
    private val binding: BeerListFragmentBinding
        get() = _binding?: throw RuntimeException("BeerListFragment = null")

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
        viewModel = ViewModelProvider(this)[BeerFavoritesViewModel::class.java]
        viewModel.beerFavoriteListLiveData.observe(viewLifecycleOwner) {
            beerFavoritesAdapter.submitList(it)
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.beerListRecycler
        with(recyclerView) {
            beerFavoritesAdapter = BeerFavoriteAdapter()
            adapter = beerFavoritesAdapter
        }
        setupClickListener()

    }

    private fun setupClickListener() {
        beerFavoritesAdapter.onBeerClick = {
            launchDetails(BeerDetailsFragment.newDetailLocalInstance(it.id))
            Log.d("TAG", "${it}")
        }
    }

    private fun launchDetails(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): BeerFavoriteFragment {
            return BeerFavoriteFragment()
        }
    }
}