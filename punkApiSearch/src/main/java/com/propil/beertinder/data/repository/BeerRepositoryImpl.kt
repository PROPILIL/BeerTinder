package com.propil.beertinder.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.propil.beertinder.data.database.BeerDao
import com.propil.beertinder.data.database.BeerDbModel
import com.propil.beertinder.data.mapper.BeerMapper
import com.propil.beertinder.data.remote.model.BeerDto
import com.propil.beertinder.data.remote.network.PunkApiPagingSource
import com.propil.beertinder.data.remote.network.PunkApiService
import com.propil.beertinder.di.ApplicationScope
import com.propil.beertinder.domain.logic.BeerRepository
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ApplicationScope
class BeerRepositoryImpl @Inject constructor(
    private val mapper: BeerMapper,
    private val beerDao: BeerDao,
    private val punkApiService: PunkApiService
) : BeerRepository {


    override fun getBeerList(): Flow<List<Beer>> {
        val response = beerDao.getBeerList()
        return response.map { list: List<BeerDbModel> ->
            list.map { item: BeerDbModel ->
                mapper.mapDbModelToEntity(item)
            }
        }
    }

    override suspend fun addBeerToFavorite(beer: Beer) {
        beer.favorite = true
        val dbModel = mapper.mapEntityToDbModel(beer)
        beerDao.addFavoriteBeer(dbModel)
    }

    override suspend fun getBeer(beerId: Int): Beer {
        val dbModel = beerDao.getBeer(beerId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun deleteBeer(beer: Beer) {
        beer.favorite = false
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

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

}

