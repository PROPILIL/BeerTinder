package com.propil.beertinder.di

import com.propil.beertinder.data.repository.BeerRepositoryImpl
import com.propil.beertinder.domain.logic.BeerRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindsRepository(impl: BeerRepositoryImpl) : BeerRepository
}