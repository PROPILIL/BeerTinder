package com.propil.beertinder.presentation.beerTinder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.propil.beertinder.data.remote.utils.ApiStatus
import com.propil.beertinder.domain.logic.AddBeerToFavoriteUseCase
import com.propil.beertinder.domain.logic.LoadRandomBeerUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeerTinderViewModel @Inject constructor(
    private val loadRandomBeerUseCase: LoadRandomBeerUseCase,
    private val addBeerToFavoriteUseCase: AddBeerToFavoriteUseCase
) : ViewModel() {

    private val _currentBeer = MutableStateFlow<ApiStatus<Beer>>(ApiStatus.loading(null))
    val currentBeer: StateFlow<ApiStatus<Beer>> = _currentBeer.asStateFlow()

    suspend fun randomBeer() = flow<ApiStatus<Beer>> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _currentBeer.value = ApiStatus.success(data = loadRandomBeerUseCase.invoke())
            } catch (e: Exception) {
                _currentBeer.value =
                    ApiStatus.error(data = null, message = "Something went wrong")
            }
        }
    }

    suspend fun addToFavorite(beer: Beer) {
        viewModelScope.launch(Dispatchers.IO) {
            addBeerToFavoriteUseCase.invoke(beer)
        }
    }
}
