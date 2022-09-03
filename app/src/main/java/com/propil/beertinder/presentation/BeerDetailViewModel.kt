package com.propil.beertinder.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.LoadBeerDetailsUseCase
import com.propil.beertinder.domain.model.Beer

class BeerDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _beer = MutableLiveData<Beer>()
    val beer: LiveData<Beer>
        get() = _beer

    private val repository = BeerRepositoryImpl(application)

    private val loadBeerDetailsUseCase = LoadBeerDetailsUseCase(repository)

    suspend fun loadBeer(beerId: Int) {
        val item = loadBeerDetailsUseCase.invoke(beerId)
        _beer.postValue(item)
    }


}