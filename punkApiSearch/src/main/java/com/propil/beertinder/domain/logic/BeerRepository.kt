package com.propil.beertinder.domain.logic

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

interface BeerRepository {

    //for the beer list
    fun getBeerList(): Flow<List<Beer>>

    //for the beer favorites
    suspend fun getBeer(beerId: Int): Beer

    suspend fun addBeerToFavorite(beer: Beer)

    //for the beerEntity favorite
    suspend fun deleteBeer(beer: Beer)

    suspend fun loadBeerList(): Flow<PagingData<Beer>>

    //for beerTinder section
    suspend fun loadRandomBeer(): Beer

    suspend fun loadBeerDetails(beerId: Int): Beer

    suspend fun loadRandomBeers(): List<Beer>
}