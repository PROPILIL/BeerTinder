package com.propil.beertinder.data.remote.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.propil.beertinder.data.remote.network.PunkApiFactory.punkApiService
import com.propil.beertinder.data.repository.BeerRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import com.propil.beertinder.di.ApplicationScope
import javax.inject.Inject
@ApplicationScope
class RemoteDataSource @Inject constructor(
    private val punkApiService: PunkApiService,
    private val punkApiPagingSource: PunkApiPagingSource
) {

    val pager = Pager(
    config = PagingConfig(
    pageSize = NETWORK_PAGE_SIZE,
    enablePlaceholders = true
    ),
    pagingSourceFactory = { punkApiPagingSource }
    ).flow


    suspend fun loadRandomBeer() = punkApiService.loadRandomBeer()

    suspend fun loadBeerDetails(id: Int) = punkApiService.loadBeerDetails(id)

}