package com.propil.beertinder.di

import androidx.lifecycle.ViewModel
import com.propil.beertinder.presentation.fragments.BeerDetailViewModel
import com.propil.beertinder.presentation.fragments.BeerFavoritesViewModel
import com.propil.beertinder.presentation.fragments.BeerListViewModel
import com.propil.beertinder.presentation.fragments.BeerTinderViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(BeerDetailViewModel::class)
    @Binds
    fun bindBeerDetailViewModel(impl: BeerDetailViewModel): ViewModel

    @IntoMap
    @ViewModelKey(BeerFavoritesViewModel::class)
    @Binds
    fun bindBeerBeerFavoritesViewModel(impl: BeerFavoritesViewModel): ViewModel

    @IntoMap
    @ViewModelKey(BeerListViewModel::class)
    @Binds
    fun bindBeerBeerListViewModel(impl: BeerListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(BeerTinderViewModel::class)
    @Binds
    fun bindBeerBeerTinderViewModel(impl: BeerTinderViewModel): ViewModel




}