package com.propil.beertinder.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.propil.beertinder.databinding.BeerDetailFragmentBinding
import com.propil.beertinder.databinding.BeerListFragmentBinding
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerDetailsFragment : Fragment() {

    private lateinit var viewmodel: BeerDetailViewModel


    private var _binding: BeerDetailFragmentBinding? = null
    private val binding: BeerDetailFragmentBinding
        get() = _binding ?: throw RuntimeException("BeerDetailBinding = null")

    private var beerId = 1


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
        launchDetails()
        addToFavorite()

    }

    private fun launchDetails() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewmodel.loadBeer(beerId)
            CoroutineScope(Dispatchers.Main).launch {
                viewmodel.beer.observe(viewLifecycleOwner) {
                    binding.beerName.text = it.name
                    binding.beerAbv.text = it.abv.toString()
                    binding.beerImage.load(it.imageUrl)
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
        if (!args.containsKey(BEER_ID)) {
            throw RuntimeException("Argument beer_id is absent!")
        } else {
            beerId = args.getInt(BEER_ID)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BEER_ID = "BEER_ID"

        fun newDetailInstance(beerId: Int): BeerDetailsFragment {
            return BeerDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(BEER_ID, beerId)
                }
            }
        }
    }

}