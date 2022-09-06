package com.propil.beertinder.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.propil.beertinder.R
import com.propil.beertinder.databinding.ActivityMainBinding
import com.propil.beertinder.presentation.fragments.BeerFavoritesFragment
import com.propil.beertinder.presentation.fragments.BeerListFragment
import com.propil.beertinder.presentation.fragments.BeerListViewModel
import com.propil.beertinder.presentation.fragments.BeerTinderFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BeerListViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[BeerListViewModel::class.java]
        navigateFragments()

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.beer_list
        } else {
            binding.bottomNavigation.selectedItemId
        }
    }

    private fun launchListFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateFragments() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.beer_list -> {
                    launchListFragment(BeerListFragment.newInstance())
                    true
                }
                R.id.beer_favorites -> {
                    launchListFragment(BeerFavoritesFragment.newInstance())
                    true
                }
                R.id.beer_tinder -> {
                    launchListFragment(BeerTinderFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

}