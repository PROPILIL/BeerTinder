package com.propil.beertinder.presentation.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.DeleteBeerUseCase
import com.propil.beertinder.domain.logic.GetBeerListUseCase
import com.propil.beertinder.domain.logic.GetBeerUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BeerFavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BeerRepositoryImpl(application)
    private val getBeerList = GetBeerListUseCase(repository)
    private val getBeer = GetBeerUseCase(repository)
    private val deleteBeer = DeleteBeerUseCase(repository)

    val beerFavoriteListLiveData = getBeerList.invoke()

    fun deleteFavBeer(beer: Beer) {
        viewModelScope.launch {
            deleteBeer.invoke(beer)
        }
    }


}