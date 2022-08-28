package com.propil.beertinder.domain.logic

import androidx.lifecycle.LiveData
import com.propil.beertinder.domain.model.Beer

interface BeerRepository {

    //for the beer list
    fun getBeerList(): LiveData<List<Beer>>

    //for the beer favorites
    suspend fun getBeer(beerId: Long): Beer

    //for beerTinder section
    suspend fun loadRandomBeer(): Beer

    //for the beerEntity favorite
    suspend fun deleteBeer(beerId: Long)

    suspend fun loadBeerList(): List<Beer>
}