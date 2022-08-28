package com.propil.beertinder.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.propil.beertinder.data.database.BeerDatabase
import com.propil.beertinder.data.mapper.BeerMapper
import com.propil.beertinder.domain.logic.BeerRepository
import com.propil.beertinder.domain.model.Beer

class BeerRepositoryImpl(application: Application): BeerRepository {

    private val beerDao = BeerDatabase.getInstance(application).beerDao()
    private val mapper = BeerMapper()

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

    override suspend fun getRandomBeer(): Beer {
        TODO("This is network fun")
    }

    override suspend fun deleteBeer(beerId: Long) {
        beerDao.deleteFavoriteBeer(beerId)
    }

    override fun loadBeerList(): LiveData<List<Beer>> {
        TODO("Not yet implemented")
    }
}