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
import com.propil.beertinder.presentation.adapters.BeerListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class BeerListFragment : Fragment() {

    private lateinit var viewModel: BeerListViewModel
    private lateinit var beerListAdapter: BeerListAdapter

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
        viewModel = ViewModelProvider(this)[BeerListViewModel::class.java]
//        viewModel.beerList.observe(viewLifecycleOwner) {
//            beerListAdapter.submitList(it)
//        }
        setupRecyclerView()
        fetchBeers()
    }

    private fun fetchBeers() {
        lifecycleScope.launch {
            viewModel.load()
                .distinctUntilChanged()
                .collectLatest { beerListAdapter.submitData(it) }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.beerListRecycler
        with(recyclerView) {
            beerListAdapter = BeerListAdapter()
            adapter = beerListAdapter
        }
        setupClickListener()

    }

    private fun setupClickListener() {
        beerListAdapter.onBeerClick = {
            launchDetails(BeerDetailsFragment.newDetailInstance(it.id))
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
        fun newInstance(): BeerListFragment {
            return BeerListFragment()
        }
    }
}