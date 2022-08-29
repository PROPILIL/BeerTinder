package com.propil.beertinder.data.remote.network

import com.propil.beertinder.data.remote.model.BeerDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkApiService {

    @GET("beers?")
    suspend fun loadBeerList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<BeerDto>//TODO: Check this parameter. Idk

    @GET("beers/random")
    suspend fun loadRandomBeer() : BeerDto
}