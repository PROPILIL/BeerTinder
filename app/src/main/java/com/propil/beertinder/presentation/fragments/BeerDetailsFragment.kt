package com.propil.beertinder.presentation.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.propil.beertinder.R
import com.propil.beertinder.data.remote.utils.Status
import com.propil.beertinder.databinding.BeerDetailFragmentBinding
import com.propil.beertinder.domain.model.Beer
import com.propil.beertinder.presentation.BeerTinderApplication
import com.propil.beertinder.presentation.utils.ToFavoriteToast
import com.propil.beertinder.presentation.utils.changeColor
import com.propil.beertinder.presentation.utils.loadWithCoil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BeerDetailsFragment : Fragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as BeerTinderApplication).component
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BeerDetailViewModel::class.java]
    }


    private var _binding: BeerDetailFragmentBinding? = null
    private val binding: BeerDetailFragmentBinding
        get() = _binding ?: throw RuntimeException("BeerDetailBinding = null")

    private var beerId = 0
    private var dataSource = UNKNOWN_DATA_SOURCE

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }


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
            viewModel.loadBeer2(beerId).collect()
            viewModel.beerDetailed.collectLatest {
                withContext(Dispatchers.Main) {
                    it.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data?.let { beer ->
                                    with(binding) {
                                        beerName.text = beer.name
                                        beerAbv.text = beer.abv.toString()
                                        beerTagline.text = beer.tagline
                                        beerImage.loadWithCoil(beer.imageUrl)
                                        beerDescription.text = beer.description
                                        beerFoodPairing.text = beer.foodPairing?.joinToString()
                                        progressBar.isVisible = false
                                        loadingError.isVisible = false
                                    }

                                }
                            }
                            Status.ERROR -> {
                                binding.progressBar.isVisible = true
                                binding.loadingError.isVisible = true
                            }

                            Status.LOADING -> {
                                binding.progressBar.isVisible = true
                                binding.loadingError.isVisible = false
                            }
                        }
                    }
                }
            }
        }
    }

    private fun inFavoriteCheck(beer: Beer) {
        if (beer.favorite == true) {
            binding.toFavoriteButton.changeColor(requireActivity(), R.color.red)
            binding.toFavoriteButton.text = getText(R.string.favorite_button_already_added)
        } else {
            binding.toFavoriteButton.text = getText(R.string.favorite_button_add)
        }
    }

    private fun launchLocalDetails() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getBeer(beerId)
            withContext(Dispatchers.Main) {
                viewModel.getBeer(beerId).collectLatest {
                    inFavoriteCheck(it)
                    binding.beerName.text = it.name
                    binding.beerAbv.text = it.abv.toString()
                    binding.beerImage.loadWithCoil(it.imageUrl)
                    binding.beerTagline.text = it.tagline
                    binding.beerDescription.text = it.description
                    binding.beerFoodPairing.text = it.foodPairing?.joinToString()
                }
            }
        }
    }

    private fun addToFavorite() {
        binding.toFavoriteButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.beerDetailed.collectLatest {
                    withContext(Dispatchers.Main) {
                        it.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    it.data?.let { currentBeer ->
                                        val beer = currentBeer.copy()
                                        viewModel.addToFavorite(beer)
                                        ToFavoriteToast.show(
                                            requireActivity(),
                                            "Added to Favorite"
                                        )
                                    }
                                }
                                Status.ERROR -> {
                                    ToFavoriteToast.show(
                                        requireActivity(),
                                        "Error. Can't add to favorite"
                                    )
                                }

                                Status.LOADING -> {
                                    ToFavoriteToast.show(
                                        requireActivity(),
                                        "Loading. Can't add to favorite"
                                    )
                                }
                            }
                        }
                    }
                }
            }
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

