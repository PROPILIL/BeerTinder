package com.propil.beertinder.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.propil.beertinder.data.PunkApiPagingSource
import com.propil.beertinder.data.database.BeerDatabase
import com.propil.beertinder.data.mapper.BeerMapper
import com.propil.beertinder.data.remote.model.BeerDto
import com.propil.beertinder.data.remote.network.PunkApiFactory
import com.propil.beertinder.domain.logic.BeerRepository
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BeerRepositoryImpl(application: Application) : BeerRepository {

    private val beerDao = BeerDatabase.getInstance(application).beerDao()
    private val mapper = BeerMapper()
    private val punkApiService = PunkApiFactory.punkApiService

    override fun getBeerList(): LiveData<List<Beer>> {
        return Transformations.map(beerDao.getBeerList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override suspend fun addBeerToFavorite(beer: Beer) {
        val dbModel = mapper.mapEntityToDbModel(beer)
        beerDao.addFavoriteBeer(dbModel)
    }

    override suspend fun getBeer(beerId: Int): Beer {
        val dbModel = beerDao.getBeer(beerId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun deleteBeer(beer: Beer) {
        beerDao.deleteFavoriteBeer(beer.id)
    }

    override suspend fun loadBeerList(): Flow<PagingData<Beer>> {
        val response = Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { PunkApiPagingSource(punkApiService) }
        ).flow

        return response.map { value: PagingData<BeerDto> ->
            value.map { mapper.mapDtoToEntity(it) }
        }
    }

    override suspend fun loadRandomBeer(): Beer {
        val response = punkApiService.loadRandomBeer()
        return mapper.mapResponseToEntity(response)
    }

    override suspend fun loadBeerDetails(beerId: Int): Beer {
        val response = punkApiService.loadBeerDetails(beerId)
        return mapper.mapResponseToEntity(response)
    }

//    fun loadBeerList(): Flow<PagingData<BeerDto>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = NETWORK_PAGE_SIZE,
//                enablePlaceholders = true
//            ),
//            pagingSourceFactory = { PunkApiPagingSource(punkApiService) }
//        ).flow
//    }

//    val flow = Pager(
//        config = PagingConfig(
//            pageSize = NETWORK_PAGE_SIZE,
//            enablePlaceholders = true
//        ),
//        pagingSourceFactory = { PunkApiPagingSource(punkApiService) }
//    ).flow

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}

