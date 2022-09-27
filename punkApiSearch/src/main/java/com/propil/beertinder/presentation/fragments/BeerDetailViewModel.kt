package com.propil.beertinder.presentation.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.propil.beertinder.data.remote.utils.ApiStatus
import com.propil.beertinder.domain.logic.AddBeerToFavoriteUseCase
import com.propil.beertinder.domain.logic.GetBeerUseCase
import com.propil.beertinder.domain.logic.LoadBeerDetailsUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeerDetailViewModel @Inject constructor(
    private val loadBeerDetailsUseCase: LoadBeerDetailsUseCase,
    private val getBeerUseCase: GetBeerUseCase,
    private val addBeerToFavoriteUseCase: AddBeerToFavoriteUseCase

) : ViewModel() {

    private val _beerDetailed = MutableStateFlow<ApiStatus<Beer>>(ApiStatus.loading(null))
    val beerDetailed: StateFlow<ApiStatus<Beer>> = _beerDetailed.asStateFlow()

    suspend fun loadBeer(beerId: Int): Flow<ApiStatus<Beer>> = flow {
        _beerDetailed.emit(ApiStatus.loading(data = null))
            try {
                _beerDetailed.emit(ApiStatus.success(data = loadBeerDetailsUseCase.invoke(beerId)))
            } catch (e: Exception) {
                _beerDetailed.emit(ApiStatus.error(data = null, message = "Something went wrong"))
            }
    }

    fun getBeer(beerId: Int): Flow<Beer> = flow {
        emit(getBeerUseCase.invoke(beerId))
    }

    fun addToFavorite(beer: Beer) {
        viewModelScope.launch(Dispatchers.IO) {
            addBeerToFavoriteUseCase.invoke(beer)
        }
    }
}