package com.propil.beertinder.domain.logic

import com.propil.beertinder.domain.model.Beer
import javax.inject.Inject

class LoadBeerDetailsUseCase @Inject constructor(
    private val repository: BeerRepository) {

    suspend operator fun invoke(beerId: Int): Beer {
        return repository.loadBeerDetails(beerId)
    }
}