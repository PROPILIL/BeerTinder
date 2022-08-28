package com.propil.beertinder.domain.logic

import com.propil.beertinder.domain.model.Beer

class GetRandomBeerUseCase(
    private val repository: BeerTinderRepository) {

    operator fun invoke(): Beer {
        return repository.getRandomBeer()
    }
}