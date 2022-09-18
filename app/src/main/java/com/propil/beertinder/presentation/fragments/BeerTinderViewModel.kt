package com.propil.beertinder.presentation.fragments

import androidx.lifecycle.*
import com.propil.beertinder.data.remote.utils.ApiStatus
import com.propil.beertinder.domain.logic.AddBeerToFavoriteUseCase
import com.propil.beertinder.domain.logic.LoadRandomBeerUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeerTinderViewModel @Inject constructor(
    private val loadRandomBeerUseCase: LoadRandomBeerUseCase,
    private val addBeerToFavoriteUseCase: AddBeerToFavoriteUseCase
) : ViewModel() {

    private val _currentBeer = MutableStateFlow<ApiStatus<Beer>>(ApiStatus.loading(null))
    val currentBeer: StateFlow<ApiStatus<Beer>> = _currentBeer.asStateFlow()

    fun randomBeer() = flow<ApiStatus<Beer>> {
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
