package com.propil.beertinder.presentation.beerList

import android.content.Context
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
import com.propil.beertinder.presentation.BeerTinderApplication
import com.propil.beertinder.presentation.adapters.BeerListAdapter
import com.propil.beertinder.presentation.details.BeerDetailsFragment
import com.propil.beertinder.presentation.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BeerListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as BeerTinderApplication).component
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BeerListViewModel::class.java]
    }

    lateinit var beerListAdapter: BeerListAdapter

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
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        initRecyclerView()
        initLoading()
        collectData()
        setupItemClickListener()
        setupRetryClickListener()

    }

    private fun collectData() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.loadBeerList()
                .distinctUntilChanged()
                .collectLatest {
                    beerListAdapter.submitData(it)
                }
        }
    }

    private fun initRecyclerView() {
        val recyclerView = binding.beerListRecycler
        with(recyclerView) {
            beerListAdapter = BeerListAdapter()
            adapter = beerListAdapter
        }
        binding.swipeRefresh.setOnRefreshListener { beerListAdapter.refresh() }
    }

    private fun initLoading() {
        lifecycleScope.launch(Dispatchers.IO) {
            beerListAdapter.loadStateFlow.collectLatest {
                withContext(Dispatchers.Main) {
                    when (it.refresh) {
                        is LoadState.NotLoading -> {
                            binding.swipeRefresh.isRefreshing = false
                            binding.loadingError.visibility = View.GONE
                        }
                        is LoadState.Loading -> {
                            binding.swipeRefresh.isRefreshing = true
                        }
                        is LoadState.Error -> {
                            binding.loadingError.visibility = View.VISIBLE
                            binding.swipeRefresh.isRefreshing = false
                        }
                    }
                    if (it.append is LoadState.Error) {
                        binding.loadingError.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupItemClickListener() {
        beerListAdapter.onBeerClick = {
            launchDetails(BeerDetailsFragment.newDetailRemoteInstance(it.id))
        }
    }

    private fun setupRetryClickListener() {
        binding.loadingError.setOnClickListener {
            beerListAdapter.refresh()
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