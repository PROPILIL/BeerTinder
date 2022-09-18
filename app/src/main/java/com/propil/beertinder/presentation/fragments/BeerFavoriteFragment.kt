package com.propil.beertinder.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.propil.beertinder.R
import com.propil.beertinder.databinding.BeerListFragmentBinding
import com.propil.beertinder.domain.model.Beer
import com.propil.beertinder.presentation.BeerTinderApplication
import com.propil.beertinder.presentation.adapters.BeerFavoriteAdapter
import com.propil.beertinder.presentation.adapters.BeerListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BeerFavoriteFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as BeerTinderApplication).component
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BeerFavoritesViewModel::class.java]
    }
    private lateinit var beerFavoritesAdapter: BeerFavoriteAdapter

    private var _binding: BeerListFragmentBinding? = null
    private val binding: BeerListFragmentBinding
        get() = _binding ?: throw RuntimeException("BeerListFragment = null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

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
        collectData()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.beerListRecycler
        with(recyclerView) {
            beerFavoritesAdapter = BeerFavoriteAdapter()
            adapter = beerFavoritesAdapter
        }
        setupClickListener()
        setupOnLongClickListener()
    }

    private fun collectData() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getBeerList()
                .distinctUntilChanged()
                .collectLatest {
                    withContext(Dispatchers.Main) {
                        beerFavoritesAdapter.submitList(it)
                    }

                }
        }

    }

    private fun setupClickListener() {
        beerFavoritesAdapter.onBeerClick = {
            launchDetails(BeerDetailsFragment.newDetailLocalInstance(it.id))
        }
    }

    private fun setupOnLongClickListener() {
        beerFavoritesAdapter.onBeerLongClick = {
            deleteAlert(it)
        }
    }

    private fun deleteAlert(beer: Beer) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Wanna delete selected beer?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Yes, delete") { _, _ ->
                viewModel.deleteFavBeer(beer)
            }.show()
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