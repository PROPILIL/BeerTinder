package com.propil.beertinder.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.propil.beertinder.R
import com.propil.beertinder.databinding.BeerDetailFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerDetailsFragment : Fragment() {

    private lateinit var viewmodel: BeerDetailViewModel


    private var _binding: BeerDetailFragmentBinding? = null
    private val binding: BeerDetailFragmentBinding
        get() = _binding ?: throw RuntimeException("BeerDetailBinding = null")

    private var beerId = 0
    private var dataSource = UNKNOWN_DATA_SOURCE


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BeerDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(this)[BeerDetailViewModel::class.java]
        parseArgs()
        launchRightMode()
        addToFavorite()

    }

    private fun launchRightMode() {
        when (dataSource) {
            BEER_LIST_FRAGMENT -> launchRemoteDetails()
            BEER_FAVORITE_FRAGMENT -> launchLocalDetails()
        }
    }

    private fun launchRemoteDetails() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewmodel.loadBeer(beerId)
            CoroutineScope(Dispatchers.Main).launch {
                viewmodel.beer.observe(viewLifecycleOwner) {
                    binding.beerName.text = it.name
                    binding.beerAbv.text = it.abv.toString()
                    binding.beerImage.load(it.imageUrl) {
                        crossfade(true)
                        placeholder(R.drawable.beer_mug)
                    }
                    binding.beerTagline.text = it.tagline
                    binding.beerDescription.text = it.description
                    binding.beerFoodPairing.text =
                        it.foodPairing?.joinToString(",", postfix = ",")
                }
            }
        }
    }

    private fun handleNetworkStatus() {
        viewmodel.requestStatus.observe(viewLifecycleOwner) {
            when (it) {
                PunkApiStatus.LOADING -> {
                    binding.progressBar.isVisible = true
                    binding.beerName.visibility = View.GONE
                    launchRemoteDetails()
                }
                PunkApiStatus.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    binding.beerName.visibility = View.VISIBLE

                }
                PunkApiStatus.ERROR -> {
                    binding.progressBar.isVisible = false
                    binding.beerName.visibility = View.GONE
                    binding.statusCode.text = "SOMETHING WENT WRONG"
                }
            }
        }
    }

    private fun launchLocalDetails() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewmodel.getBeer(beerId)
            CoroutineScope(Dispatchers.Main).launch {
                viewmodel.favoriteBeer.observe(viewLifecycleOwner) {
                    binding.beerName.text = it.name
                    binding.beerAbv.text = it.abv.toString()
                    binding.beerImage.load(it.imageUrl) {
                        crossfade(true)
                        placeholder(R.drawable.beer_mug)
                    }
                    binding.beerTagline.text = it.tagline
                    binding.beerDescription.text = it.description
                    binding.beerFoodPairing.text = it.foodPairing?.joinToString(",", postfix = ",")
                }
            }
        }
    }

    private fun addToFavorite() {
        binding.toFavoriteButton.setOnClickListener {
            viewmodel.addToFavorite()
        }
    }

    private fun parseArgs() {
        val args = requireArguments()
        if (!args.containsKey(DATA_SOURCE)) {
            throw RuntimeException("Argument DATA_SOURCE is absent!")
        }
        val source = args.getString(DATA_SOURCE)
        if (source != BEER_LIST_FRAGMENT && source != BEER_FAVORITE_FRAGMENT) {
            throw RuntimeException("Unknown DATA_SOURCE $dataSource")
        }
        dataSource = source
        if (dataSource == BEER_LIST_FRAGMENT || dataSource == BEER_FAVORITE_FRAGMENT) {
            if (!args.containsKey(BEER_ID)) {
                throw RuntimeException("Argument BEER_ID is absent!")
            }
            beerId = args.getInt(BEER_ID)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BEER_ID = "BEER_ID"
        private const val DATA_SOURCE = "DATA_SOURCE"
        private const val BEER_LIST_FRAGMENT = "BEER_LIST_FRAGMENT"
        private const val BEER_FAVORITE_FRAGMENT = "BEER_FAVORITE_FRAGMENT"
        private const val UNKNOWN_DATA_SOURCE = ""

        fun newDetailRemoteInstance(beerId: Int): BeerDetailsFragment {
            return BeerDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(DATA_SOURCE, BEER_LIST_FRAGMENT)
                    putInt(BEER_ID, beerId)
                }
            }
        }

        fun newDetailLocalInstance(beerId: Int): BeerDetailsFragment {
            return BeerDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(DATA_SOURCE, BEER_FAVORITE_FRAGMENT)
                    putInt(BEER_ID, beerId)
                }
            }
        }
    }

}