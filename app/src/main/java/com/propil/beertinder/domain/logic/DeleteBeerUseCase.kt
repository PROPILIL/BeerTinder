package com.propil.beertinder.domain.logic

import com.propil.beertinder.domain.model.Beer

class DeleteBeerUseCase(
    private val repository: BeerRepository
) {
    suspend operator fun invoke(beerId: Long) {
        repository.deleteBeer(beerId)
    }
}