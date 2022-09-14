package com.propil.beertinder.di

import androidx.lifecycle.ViewModel
import dagger.Component

@Component(modules = [DomainModule::class, ViewModelModule::class])
interface Component {


}