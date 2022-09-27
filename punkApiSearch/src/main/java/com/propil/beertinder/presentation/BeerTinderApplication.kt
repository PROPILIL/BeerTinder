package com.propil.beertinder.presentation

import android.app.Application
import com.propil.beertinder.di.DaggerApplicationComponent

class BeerTinderApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }


}