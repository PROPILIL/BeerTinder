package com.propil.beertinder.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

private const val BASE_URL = "https://api.punkapi.com/v2/"

object PunkApiFactory {

    private val contentType = "application/json".toMediaType()
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(getLoggingInterceptor())
        .build()

    private fun getLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    fun retrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory(contentType))
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()

    val punkApi: PunkApiService = retrofit().create(PunkApiService::class.java)
}