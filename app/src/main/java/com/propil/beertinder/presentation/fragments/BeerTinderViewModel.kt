package com.propil.beertinder.presentation.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.LoadBeerListUseCase
import com.propil.beertinder.domain.logic.LoadRandomBeerUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.launch

class BeerTinderViewModel(application: Application) : AndroidViewModel(application) {

    private val _randomBeer = MutableLiveData<Beer>()
    val randomBeer: LiveData<Beer>
        get() = _randomBeer

    private val repository = BeerRepositoryImpl(application)

    private val loadRandomBeerUseCase = LoadRandomBeerUseCase(repository)

    suspend fun loadRandomBeer() {
        viewModelScope.launch {
            val item = loadRandomBeerUseCase.invoke()
            _randomBeer.postValue(item)
        }

    }
}