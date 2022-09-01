package com.propil.beertinder.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.propil.beertinder.data.remote.network.PunkApiFactory
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.LoadBeerListUseCase
import com.propil.beertinder.domain.logic.LoadRandomBeerUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.Dispatchers

class TestViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BeerRepositoryImpl(application)
    private val loadBeerList = LoadBeerListUseCase(repository)
    private val punkApiService = PunkApiFactory.punkApiService
    private val loadRandomBeerUseCase = LoadRandomBeerUseCase(repository)




    val randomBeer = liveData(Dispatchers.IO) {
        val response = loadRandomBeerUseCase.invoke()
        emit(response)
    }

    val beerList = liveData(Dispatchers.IO) {
        val response = loadBeerList.invoke(1, 10)
        emit(response)
    }
}

