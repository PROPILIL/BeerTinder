package com.propil.beertinder.presentation.beerTinder

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.propil.beertinder.data.remote.utils.Status
import com.propil.beertinder.databinding.BeerTinderFragmentBinding
import com.propil.beertinder.domain.model.Beer
import com.propil.beertinder.presentation.BeerTinderApplication
import com.propil.beertinder.presentation.ViewModelFactory
import com.propil.beertinder.presentation.adapters.BeerTinderAdapter
import com.propil.beertinder.presentation.utils.ToFavoriteToast
import com.propil.beertinder.presentation.utils.loadWithCoil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BeerTinderFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as BeerTinderApplication).component
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BeerTinderViewModel::class.java]
    }

    private lateinit var beerTinderAdapter: BeerTinderAdapter


    private var _binding: BeerTinderFragmentBinding? = null
    private val binding: BeerTinderFragmentBinding
        get() = _binding ?: throw RuntimeException("BeerDetailBinding = null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BeerTinderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        loadRandomBeer()
//        newBeer()
//        retryButton()
//        yesButtonClick()
//        noButtonClick()
        setupViewPager()
        collectData()
    }


    private fun setupViewPager() {
        with(binding.tinderCardStack) {
            beerTinderAdapter = BeerTinderAdapter()
            adapter = beerTinderAdapter
            offscreenPageLimit = 3

        }
    }

    private fun collectData() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.randomBeers().collect()
            viewModel.currentBeers.collectLatest {
                withContext(Dispatchers.Main) {
                    it.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data?.let { list: List<Beer> ->
                                    beerTinderAdapter.submitList(list)
                                    Log.e("TINDER", resource.data.toString())
                                }
                            }
                            Status.ERROR -> {
                                Log.e("TINDER", "ERROR")
                            }

                            Status.LOADING -> {
                                Log.e("TINDER", "LOADING")
                            }
                        }
                    }
                }
            }
        }
    }

//    private fun loadRandomBeer() {
//        lifecycleScope.launch(Dispatchers.IO) {
//            viewModel.randomBeer().collect()
//            viewModel.currentBeer.collectLatest {
//                withContext(Dispatchers.Main) {
//                    it.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                resource.data?.let { beer ->
//                                    binding.beerName.text = beer.name
//                                    binding.beerAbv.text = beer.abv.toString()
//                                    binding.beerImage.loadWithCoil(beer.imageUrl)
//                                    binding.beerTagline.text = beer.tagline
//                                    binding.progressBar.isVisible = false
//                                    binding.loadingError.isVisible = false
//                                }
//                            }
//                            Status.ERROR -> {
//                                binding.progressBar.isVisible = true
//                                binding.loadingError.isVisible = true
//                            }
//
//                            Status.LOADING -> {
//                                binding.progressBar.isVisible = true
//                                binding.loadingError.isVisible = false
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

//    private fun retryButton() {
//        binding.loadingError.setOnClickListener {
//            loadRandomBeer()
//        }
//    }
//
//    private fun newBeer() {
//        binding.tinderCard.setOnClickListener {
//            loadRandomBeer()
//        }
//    }
//
//    private fun yesButtonClick() {
//        binding.yesButton.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.Main) {
//                val value = viewModel.currentBeer.value
//                value.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            resource.data?.let { currentBeer ->
//                                viewModel.addToFavorite(currentBeer)
//                                ToFavoriteToast.show(
//                                    requireActivity(),
//                                    "Added to Favorite"
//                                )
//                            }
//                        }
//                        Status.ERROR -> {
//                            ToFavoriteToast.show(
//                                requireActivity(),
//                                "Error. Can't add to favorite"
//                            )
//                        }
//
//                        Status.LOADING -> {
//                            ToFavoriteToast.show(
//                                requireActivity(),
//                                "Loading. Can't add to favorite"
//                            )
//                        }
//                    }
//                }
//            }
//            loadRandomBeer()
//        }
//    }
//
//    private fun noButtonClick() {
//        binding.noButton.setOnClickListener {
//            loadRandomBeer()
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): BeerTinderFragment {
            return BeerTinderFragment()
        }
    }
}