package com.propil.beertinder.presentation.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class BeerTinderFragment: Fragment() {


//    val randomBeer = liveData(Dispatchers.IO) {
//        val response = loadRandomBeerUseCase.invoke()
//        _selectedBeer.postValue(response)
//        emit(response)
//    }
companion object {
    fun newInstance(): BeerTinderFragment {
        return BeerTinderFragment()
    }
}
}