package com.propil.beertinder.network

import com.propil.beertinder.data.remote.BeerDto
import retrofit2.http.GET

interface PunkApiService {
    @GET(".beers")
    suspend fun getBeers(): List<BeerDto> //TODO: Think about this list

    @GET(".beers/random")
    suspend fun getRandomBeer(): BeerDto
}

//@Query("page") page: Int,
//                 @Query("per_page") beersPerPage: Int
