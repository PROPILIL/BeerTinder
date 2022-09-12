package com.propil.beertinder.presentation.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.Flow

class BeerListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BeerRepositoryImpl(application)

    suspend fun loadBeerList(): Flow<PagingData<Beer>> {
        return repository.loadBeerList().cachedIn(viewModelScope)
    }

}

