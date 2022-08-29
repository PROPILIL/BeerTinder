package com.propil.beertinder.domain.logic

import com.propil.beertinder.domain.model.Beer

class GetBeerUseCase(
    private val repository: BeerRepository
) {
    suspend operator fun invoke(beerId: Long): Beer {
        return repository.getBeer(beerId)
    }
}