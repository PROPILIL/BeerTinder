package com.propil.beertinder.data.remote.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.propil.beertinder.data.remote.network.PunkApiFactory.punkApiService
import com.propil.beertinder.data.repository.BeerRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val punkApiService: PunkApiService
) {

    suspend fun loadRandomBeer() = punkApiService.loadRandomBeer()

    suspend fun loadBeerDetails(id: Int) = punkApiService.loadBeerDetails(id)

}