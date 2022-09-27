package com.propil.beertinder.presentation.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.LoadBeerListUseCase
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BeerListViewModel @Inject constructor(
    private val loadBeerListUseCase: LoadBeerListUseCase
): ViewModel() {

    suspend fun loadBeerList(): Flow<PagingData<Beer>> {
        return loadBeerListUseCase.invoke().cachedIn(viewModelScope)
    }

}

