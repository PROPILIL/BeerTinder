package com.propil.beertinder.domain.logic

import androidx.lifecycle.LiveData
import com.propil.beertinder.domain.model.Beer

interface BeerTinderRepository {

    //for the beer list
    fun getBeerList() : LiveData<List<Beer>>

    //for the beer favorites and beer "tinder"
    fun getBeer(beerId: Long): Beer

    //for the beer favorite
    fun deleteBeer(beer: Beer)
}