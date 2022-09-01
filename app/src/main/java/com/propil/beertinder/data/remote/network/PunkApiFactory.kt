package com.propil.beertinder.data.remote.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

object PunkApiFactory {

    private const val BASE_URL = "https://api.punkapi.com/v2/"
     val contentType = "application/json".toMediaType()

    private fun createOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY





        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory(contentType))
        .client(createOkHttpClient())
        .baseUrl(BASE_URL)
        .build()

    // an apiService instance
    val punkApiService: PunkApiService = retrofit.create(PunkApiService::class.java)
}