package com.propil.beertinder.domain.logic

import androidx.lifecycle.LiveData
import com.propil.beertinder.domain.model.Beer

interface BeerRepository {

    //for the beer list
    fun getBeerList(): LiveData<List<Beer>>

    //for the beer favorites
    suspend fun getBeer(beerId: Long): Beer

    //for beerTinder section
    suspend fun getRandomBeer(): Beer

    //for the beerEntity favorite
    suspend fun deleteBeer(beerId: Long)

    fun loadBeerList(): LiveData<List<Beer>>
}