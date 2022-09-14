package com.propil.beertinder.presentation.fragments

import android.app.Application
import androidx.lifecycle.*
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.DeleteBeerUseCase
import com.propil.beertinder.domain.logic.GetBeerListUseCase
import com.propil.beertinder.domain.logic.GetBeerUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeerFavoritesViewModel @Inject constructor(
    getBeerList: GetBeerListUseCase,
    private val deleteBeer: DeleteBeerUseCase
) : ViewModel() {


    val beerFavoriteListLiveData = getBeerList.invoke()

    fun deleteFavBeer(beer: Beer) {
        viewModelScope.launch {
            deleteBeer.invoke(beer)
        }
    }


}