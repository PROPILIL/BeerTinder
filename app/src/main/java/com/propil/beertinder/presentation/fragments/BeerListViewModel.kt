package com.propil.beertinder.presentation.fragments

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.propil.beertinder.data.mapper.BeerMapper
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BeerListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BeerRepositoryImpl(application)
    private val mapper = BeerMapper()

    suspend fun loadBeerList(): Flow<PagingData<Beer>> {
        return repository.loadBeerList().cachedIn(viewModelScope)
    }

}

