package com.propil.beertinder.domain.logic

import com.propil.beertinder.domain.model.Beer

class DeleteBeerUseCase(
    private val repository: BeerTinderRepository
) {
    operator fun invoke(beer: Beer) {
        repository.deleteBeer(beer)
    }
}