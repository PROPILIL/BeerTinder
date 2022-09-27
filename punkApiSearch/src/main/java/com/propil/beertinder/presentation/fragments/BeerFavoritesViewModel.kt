package com.propil.beertinder.presentation.fragments

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.DeleteBeerUseCase
import com.propil.beertinder.domain.logic.GetBeerListUseCase
import com.propil.beertinder.domain.logic.GetBeerUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeerFavoritesViewModel @Inject constructor(
    private val getBeerListUseCase: GetBeerListUseCase,
    private val deleteBeerUseCase: DeleteBeerUseCase
) : ViewModel() {


    fun getBeerList(): Flow<List<Beer>> {
        return getBeerListUseCase.invoke()

    }

    fun deleteFavBeer(beer: Beer) {
        viewModelScope.launch {
            deleteBeerUseCase.invoke(beer)
        }
    }


}