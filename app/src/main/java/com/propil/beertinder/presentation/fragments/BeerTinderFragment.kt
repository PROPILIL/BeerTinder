package com.propil.beertinder.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.propil.beertinder.databinding.BeerTinderFragmentBinding
import com.propil.beertinder.presentation.BeerTinderApplication
import com.propil.beertinder.presentation.utils.ImageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        fetchRandomBeer()
        newBeer()
    }

    private fun fetchRandomBeer() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.loadRandomBeer()
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.randomBeer.observe(viewLifecycleOwner) {
                    binding.beerName.text = it.name
                    binding.beerAbv.text = it.abv.toString()
                    ImageLoader.loadImageWithCoil(binding.beerImage, it.imageUrl)
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