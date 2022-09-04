package com.propil.beertinder.domain.logic

import com.propil.beertinder.domain.model.Beer

class AddBeerToFavoriteUseCase(private val repository: BeerRepository) {

    suspend operator fun invoke(beer: Beer) {
        repository.addBeerToFavorite(beer)
    }
}