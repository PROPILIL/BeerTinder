package com.propil.beertinder.data.remote.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

object PunkApiFactory {

    private const val BASE_URL = "https://api.punkapi.com/v2/"
    private val contentType = "application/json".toMediaType()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory(contentType))
        .baseUrl(BASE_URL)
        .build()

    val punkApiService = retrofit.create(PunkApiService::class.java)
}