package com.propil.beertinder.di

import android.app.Application
import com.propil.beertinder.presentation.details.BeerDetailsFragment
import com.propil.beertinder.presentation.favorite.BeerFavoriteFragment
import com.propil.beertinder.presentation.beerList.BeerListFragment
import com.propil.beertinder.presentation.beerTinder.BeerTinderFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules =
    [DataModule::class, ViewModelModule::class, RemoteDataModule::class]
)
interface ApplicationComponent {

    fun inject(beerListFragment: BeerListFragment)

    fun inject(beerDetailsFragment: BeerDetailsFragment)

    fun inject(beerFavoriteFragment: BeerFavoriteFragment)

    fun inject(beerTinderFragment: BeerTinderFragment)

    @Component.Factory
    interface ComponentFactory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}