package com.propil.beertinder.domain.logic

import androidx.lifecycle.LiveData
import com.propil.beertinder.domain.model.Beer
import kotlinx.serialization.json.Json

interface BeerRepository {

    //for the beer list
    fun getBeerList(): LiveData<List<Beer>>

    //for the beer favorites
    suspend fun getBeer(beerId: Int): Beer

    //for the beerEntity favorite
    suspend fun deleteBeer(beerId: Int)

    suspend fun loadBeerList(page: Int, per_page: Int): List<Beer>

    //for beerTinder section
    suspend fun loadRandomBeer(): Beer


}