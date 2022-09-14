package com.propil.beertinder.di

import android.app.Application
import com.propil.beertinder.presentation.fragments.BeerDetailsFragment
import com.propil.beertinder.presentation.fragments.BeerFavoriteFragment
import com.propil.beertinder.presentation.fragments.BeerListFragment
import com.propil.beertinder.presentation.fragments.BeerTinderFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(beerListFragment: BeerListFragment)

    fun inject(beerDetailsFragment: BeerDetailsFragment)

    fun inject(beerFavoriteFragment: BeerFavoriteFragment)

    fun inject(beerTinderFragment: BeerTinderFragment)

    @Component.Factory
    interface ComponentFactory {

        fun create(
            @BindsInstance application: Application
        ) : ApplicationComponent
    }
}