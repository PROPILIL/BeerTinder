package com.propil.beertinder.di

import android.app.Application
import com.propil.beertinder.data.database.BeerDao
import com.propil.beertinder.data.database.BeerDatabase
import com.propil.beertinder.data.remote.network.PunkApiService
import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.BeerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
interface DataModule {

    @Binds
    fun bindsRepository(impl: BeerRepositoryImpl) : BeerRepository


    companion object {

        @Provides
        fun provideBeerDao(
            application: Application
        ): BeerDao {
            return BeerDatabase.getInstance(application).beerDao()
        }
    }
}