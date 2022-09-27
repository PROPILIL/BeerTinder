package com.propil.beertinder.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.propil.beertinder.R
import com.propil.beertinder.databinding.ActivityMainBinding
import com.propil.beertinder.presentation.fragments.BeerFavoriteFragment
import com.propil.beertinder.presentation.fragments.BeerListFragment
import com.propil.beertinder.presentation.fragments.BeerTinderFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BeerTinder_Launcher)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        navigateFragments()

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.beer_list
            launchFragment(BeerListFragment.newInstance())

        } else {
            binding.bottomNavigation.selectedItemId
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateFragments() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.beer_list -> {
                    launchFragment(BeerListFragment.newInstance())
                    true
                }
                R.id.beer_favorites -> {
                    launchFragment(BeerFavoriteFragment.newInstance())
                    true
                }
                R.id.beer_tinder -> {
                    launchFragment(BeerTinderFragment.newInstance())
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.beer_list -> Unit
                R.id.beer_favorites -> Unit
                R.id.beer_tinder -> Unit
            }
        }
    }

}