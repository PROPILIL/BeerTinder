package com.propil.beertinder.data.remote.network

import com.propil.beertinder.data.remote.model.BeerDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PunkApiService {

    @GET("beers?")
    suspend fun loadBeerList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<BeerDto>>

    @GET("beers/random")
    suspend fun loadRandomBeer(): Response<List<BeerDto>>

    @GET("beers/{id}")
    suspend fun loadBeerDetails(@Path("id") id: Int): Response<List<BeerDto>>

}