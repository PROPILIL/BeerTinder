package com.propil.beertinder.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.propil.beertinder.data.database.BeerDatabase
import com.propil.beertinder.data.mapper.BeerMapper
import com.propil.beertinder.data.remote.model.BeerDto
import com.propil.beertinder.data.remote.network.PunkApiFactory
import com.propil.beertinder.data.remote.network.PunkApiService
import com.propil.beertinder.domain.logic.BeerRepository
import com.propil.beertinder.domain.model.Beer

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

    override suspend fun getBeer(beerId: Long): Beer {
        val dbModel = beerDao.getBeer(beerId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun loadRandomBeer(): Beer {
        val modelDto = punkApiService.loadRandomBeer()
        return mapper.mapDtoToEntity(modelDto)
    }

    override suspend fun deleteBeer(beerId: Long) {
        beerDao.deleteFavoriteBeer(beerId)
    }

    override suspend fun loadBeerList(page: Int, per_page: Int): List<Beer> {
        val dtoList = punkApiService.loadBeerList(page, per_page)
        return dtoList.map {
            mapper.mapDtoToEntity(it)
        }
    }
}