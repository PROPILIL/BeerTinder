package com.propil.beertinder.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.propil.beertinder.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[TestViewModel::class.java]
        lifecycleScope.launch(Dispatchers.Main) {
            loadText()
            loadList()

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
}