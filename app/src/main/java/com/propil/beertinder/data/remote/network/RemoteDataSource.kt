package com.propil.beertinder.data.remote.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.propil.beertinder.data.remote.model.BeerDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val punkApiService: PunkApiService,
) {

    suspend fun loadRandomBeer() = punkApiService.loadRandomBeer()

    suspend fun loadBeerDetails(id: Int) = punkApiService.loadBeerDetails(id)

}