package com.propil.beertinder.domain.logic

import androidx.lifecycle.LiveData
import com.propil.beertinder.domain.model.Beer

interface BeerTinderRepository {

    //for the beer list
    fun getBeerList() : LiveData<List<Beer>>

    //for the beer favorites
    fun getBeer(beerId: Long): Beer

    //for beerTinder section
    fun getRandomBeer(): Beer

    //for the beerEntity favorite
    fun deleteBeer(beerEntity: Beer)
}