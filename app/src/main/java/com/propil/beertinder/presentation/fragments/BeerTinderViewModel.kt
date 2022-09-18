package com.propil.beertinder.presentation.fragments

import androidx.lifecycle.*
import com.propil.beertinder.data.remote.utils.Resource
import com.propil.beertinder.domain.logic.AddBeerToFavoriteUseCase
import com.propil.beertinder.domain.logic.LoadRandomBeerUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeerTinderViewModel @Inject constructor(
    private val loadRandomBeerUseCase: LoadRandomBeerUseCase,
    private val addBeerToFavoriteUseCase: AddBeerToFavoriteUseCase
) : ViewModel() {

    private val _currentBeer = MutableStateFlow<Resource<Beer>>(Resource.loading(null))
    val currentBeer: StateFlow<Resource<Beer>> = _currentBeer.asStateFlow()

    fun randomBeer() = flow<Resource<Beer>> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _currentBeer.value = Resource.success(data = loadRandomBeerUseCase.invoke())
            } catch (e: Exception) {
                _currentBeer.value =
                    Resource.error(data = null, message = "Something went wrong")
            }
        }
    }

    suspend fun toFavorite(beer: Beer) {
        viewModelScope.launch(Dispatchers.IO) {
            addBeerToFavoriteUseCase.invoke(beer)
        }
    }
}
