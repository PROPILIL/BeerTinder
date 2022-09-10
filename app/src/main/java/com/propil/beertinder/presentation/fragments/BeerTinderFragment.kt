package com.propil.beertinder.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.propil.beertinder.R
import com.propil.beertinder.databinding.BeerTinderFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerTinderFragment : Fragment() {

    private lateinit var viewmodel: BeerTinderViewModel


    private var _binding: BeerTinderFragmentBinding? = null
    private val binding: BeerTinderFragmentBinding
        get() = _binding ?: throw RuntimeException("BeerDetailBinding = null")

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
        viewmodel = ViewModelProvider(this)[BeerTinderViewModel::class.java]
        fetchRandomBeer()
        newBeer()
    }

    private fun fetchRandomBeer() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewmodel.loadRandomBeer()
            CoroutineScope(Dispatchers.Main).launch {
                viewmodel.randomBeer.observe(viewLifecycleOwner) {
                    binding.beerName.text = it.name
                    binding.beerAbv.text = it.abv.toString()
                    binding.beerImage.load(it.imageUrl) {
                        crossfade(true)
                        placeholder(R.drawable.beer_mug)
                    }
                    binding.beerTagline.text = it.tagline
                }
            }
        }
    }

    private fun newBeer(){
        binding.tinderCard.setOnClickListener{
            fetchRandomBeer()
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