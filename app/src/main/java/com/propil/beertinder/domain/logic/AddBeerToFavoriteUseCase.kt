package com.propil.beertinder.domain.logic

import com.propil.beertinder.domain.model.Beer
import javax.inject.Inject

class AddBeerToFavoriteUseCase @Inject constructor(
    private val repository: BeerRepository) {

    suspend operator fun invoke(beer: Beer) {
        repository.addBeerToFavorite(beer)
    }
}