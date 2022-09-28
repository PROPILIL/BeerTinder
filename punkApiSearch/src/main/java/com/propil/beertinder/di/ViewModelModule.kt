package com.propil.beertinder.di

import androidx.lifecycle.ViewModel
import com.propil.beertinder.presentation.details.BeerDetailViewModel
import com.propil.beertinder.presentation.favorite.BeerFavoritesViewModel
import com.propil.beertinder.presentation.beerList.BeerListViewModel
import com.propil.beertinder.presentation.beerTinder.BeerTinderViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


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