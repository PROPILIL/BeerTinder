package com.propil.beertinder.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.propil.beertinder.R
import com.propil.beertinder.databinding.ActivityMainBinding
import com.propil.beertinder.presentation.favorite.BeerFavoriteFragment
import com.propil.beertinder.presentation.beerList.BeerListFragment
import com.propil.beertinder.presentation.beerTinder.BeerTinderFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BeerTinder_Launcher)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        navigate()

    }

    private fun navigate(){
        val bottomNavMenu = binding.bottomNavigation
        val navHostFragment = findNavController(R.id.fragment_container)
        bottomNavMenu.setupWithNavController(navHostFragment)
    }

}