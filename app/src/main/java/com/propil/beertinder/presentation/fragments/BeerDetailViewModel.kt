package com.propil.beertinder.presentation.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.AddBeerToFavoriteUseCase
import com.propil.beertinder.domain.logic.GetBeerUseCase
import com.propil.beertinder.domain.logic.LoadBeerDetailsUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

enum class PunkApiStatus { LOADING, SUCCESS, ERROR }

class BeerDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _beer = MutableLiveData<Beer>()
    val beer: LiveData<Beer>
        get() = _beer

    private val _favoriteBeer = MutableLiveData<Beer>()
    val favoriteBeer: LiveData<Beer>
        get() = _favoriteBeer

    private val _requestStatus = MutableLiveData<PunkApiStatus>()
    val requestStatus: LiveData<PunkApiStatus>
        get() = _requestStatus

    private val repository = BeerRepositoryImpl(application)

    private val loadBeerDetailsUseCase = LoadBeerDetailsUseCase(repository)
    private val addBeerToFavoriteUseCase = AddBeerToFavoriteUseCase(repository)
    private val getBeerUseCase = GetBeerUseCase(repository)

    suspend fun loadBeer(beerId: Int) {
        viewModelScope.launch {
            _requestStatus.postValue(PunkApiStatus.LOADING)
            try {
                _beer.postValue(loadBeerDetailsUseCase.invoke(beerId))
                _requestStatus.postValue(PunkApiStatus.SUCCESS)

            } catch (e: Exception) {
                _requestStatus.postValue(PunkApiStatus.ERROR)
            }

        }
    }

    fun getBeer(beerId: Int) {
        viewModelScope.launch {
            val item = getBeerUseCase.invoke(beerId)
            _favoriteBeer.postValue(item)
        }
    }

    fun addToFavorite() {
        viewModelScope.launch {
            _beer.value?.let {
                val item = it.copy()
                addBeerToFavoriteUseCase.invoke(item)
            }
        }

    }


}