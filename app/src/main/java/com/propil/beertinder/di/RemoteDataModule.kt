package com.propil.beertinder.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.propil.beertinder.data.remote.network.PunkApiService
import com.propil.beertinder.data.remote.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


@Module
class RemoteDataModule {

    @Provides
    fun provideBaseUrl(): String = "https://api.punkapi.com/v2/"

    @Provides
    fun provideConverterFactory(): MediaType = "application/json".toMediaType()

    @ApplicationScope
    @Provides
    fun provideRetrofitService(BASE_URL: String, jsonConverter: MediaType): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(jsonConverter))
            .baseUrl(BASE_URL)
            .build()
    }

    @ApplicationScope
    @Provides
    fun providePunkApiService(retrofit: Retrofit): PunkApiService {
        return retrofit.create(PunkApiService::class.java)
    }

    @ApplicationScope
    @Provides
    fun provideRemoteDataSource(punkApiService: PunkApiService): RemoteDataSource {
        return RemoteDataSource(punkApiService)
    }
}