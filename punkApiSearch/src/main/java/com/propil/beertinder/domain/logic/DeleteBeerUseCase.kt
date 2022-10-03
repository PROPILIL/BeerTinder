package com.propil.beertinder.domain.logic

import com.propil.beertinder.domain.model.Beer
import javax.inject.Inject

class DeleteBeerUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend operator fun invoke(beer: Beer) = repository.deleteBeer(beer)
}