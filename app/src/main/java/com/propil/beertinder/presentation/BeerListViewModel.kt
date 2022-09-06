package com.propil.beertinder.presentation

import android.app.Activity
import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.propil.beertinder.data.mapper.BeerMapper
import com.propil.beertinder.data.remote.model.BeerDto
import com.propil.beertinder.data.remote.network.PunkApiFactory
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.LoadBeerDetailsUseCase
import com.propil.beertinder.domain.logic.LoadBeerListUseCase
import com.propil.beertinder.domain.logic.LoadRandomBeerUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BeerListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BeerRepositoryImpl(application)
    private val loadBeerList = LoadBeerListUseCase(repository)
    private val mapper = BeerMapper()

    val beerList = liveData(Dispatchers.IO) {
        val response = loadBeerList.invoke(1, 10)
        emit(response)
    }

    private fun load(): Flow<PagingData<Beer>> {
        return repository.loadData()
            .map {
                it.map { mapper.mapDtoToEntity(it) }
            }.cachedIn(viewModelScope)
    }
}

