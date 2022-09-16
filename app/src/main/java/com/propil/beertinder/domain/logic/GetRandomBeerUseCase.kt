package com.propil.beertinder.domain.logic

import com.propil.beertinder.domain.model.Beer

class GetRandomBeerUseCase(
    private val repository: BeerRepository) {

    suspend operator fun invoke(): Beer {
        return repository.getRandomBeer()
    }
}