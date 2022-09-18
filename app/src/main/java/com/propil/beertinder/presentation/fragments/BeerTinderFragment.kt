package com.propil.beertinder.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.propil.beertinder.data.remote.utils.Status
import com.propil.beertinder.databinding.BeerTinderFragmentBinding
import com.propil.beertinder.presentation.BeerTinderApplication
import com.propil.beertinder.presentation.utils.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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
        loadRandomBeer()
        newBeer()
        retryButton()
        yesButtonClick()
        noButtonClick()
    }

    private fun loadRandomBeer() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.randomBeer().collect()
            viewModel.currentBeer.collectLatest {
                withContext(Dispatchers.Main) {
                    it.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data?.let { beer ->
                                    binding.beerName.text = beer.name
                                    binding.beerAbv.text = beer.abv.toString()
                                    ImageLoader.loadImageWithCoil(binding.beerImage, beer.imageUrl)
                                    binding.beerTagline.text = beer.tagline
                                    binding.progressBar.isVisible = false
                                    binding.loadingError.isVisible = false
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

    private fun retryButton() {
        binding.loadingError.setOnClickListener {
            loadRandomBeer()
        }
    }

    private fun newBeer() {
        binding.tinderCard.setOnClickListener {
            loadRandomBeer()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireActivity().application,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun yesButtonClick() {
        binding.yesButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.currentBeer.collectLatest {
                    withContext(Dispatchers.Main) {
                        it.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    it.data?.let { currentBeer ->
                                        val beer = currentBeer.copy()
                                        viewModel.addToFavorite(beer)
                                        showToast("Added to favorite")
                                    }
                                }
                                Status.ERROR -> {
                                    showToast("Error. Can't add to favorite")
                                }

                                Status.LOADING -> {
                                    showToast("Loading. Can't add to favorite")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun noButtonClick() {
        binding.noButton.setOnClickListener {
            loadRandomBeer()
        }
    }

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