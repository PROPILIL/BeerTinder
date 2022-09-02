package com.propil.beertinder.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.propil.beertinder.data.database.BeerDatabase
import com.propil.beertinder.data.mapper.BeerMapper
import com.propil.beertinder.data.remote.network.PunkApiFactory
import com.propil.beertinder.domain.logic.BeerRepository
import com.propil.beertinder.domain.model.Beer
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.IOException

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

    override suspend fun getBeer(beerId: Int): Beer {
        val dbModel = beerDao.getBeer(beerId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun deleteBeer(beerId: Int) {
        beerDao.deleteFavoriteBeer(beerId)
    }

    override suspend fun loadBeerList(page: Int, per_page: Int): List<Beer> {
        val response = punkApiService.loadBeerList(page, per_page)
        return mapper.mapResponseListToEntityList(response)
    }

    override suspend fun loadRandomBeer(): Beer {
        val response = punkApiService.loadRandomBeer()
        return mapper.mapResponseToEntity(response)
    }

    override suspend fun loadBeerDetails(beerId: Int): Beer {
        val response = punkApiService.loadBeerDetails(beerId)
        return mapper.mapResponseToEntity(response)
    }
}

