package com.propil.beertinder.presentation.fragments

import android.app.Application
import androidx.lifecycle.*
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.LoadBeerListUseCase
import com.propil.beertinder.domain.logic.LoadRandomBeerUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeerTinderViewModel @Inject constructor(
    private val loadRandomBeerUseCase: LoadRandomBeerUseCase
) : ViewModel() {

    private val _randomBeer = MutableLiveData<Beer>()
    val randomBeer: LiveData<Beer>
        get() = _randomBeer


    suspend fun loadRandomBeer() {
        viewModelScope.launch {
            val item = loadRandomBeerUseCase.invoke()
            _randomBeer.postValue(item)
        }

    }
}