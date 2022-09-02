package com.propil.beertinder.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.propil.beertinder.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BeerListViewModel
    private lateinit var button: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        button.setOnClickListener {
            loadDetails()
        }
        viewModel = ViewModelProvider(this)[BeerListViewModel::class.java]
        lifecycleScope.launch(Dispatchers.Main) {
            loadText()

        }

    }

    private fun loadText() {
        viewModel.randomBeer.observe(this, Observer {
            Log.d("TAG", "$it")
        })
    }

    private fun loadList(){
        viewModel.beerList.observe(this, Observer {
            Log.d("TAG", "$it")
        })
    }

    private fun loadDetails(){
        viewModel.beerDetails.observe(this, Observer {
            Log.d("TAG", "$it")
        })
    }
}