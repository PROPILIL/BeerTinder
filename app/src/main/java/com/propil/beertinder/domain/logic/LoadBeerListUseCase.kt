package com.propil.beertinder.domain.logic

import javax.inject.Inject

class LoadBeerListUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend operator fun invoke() = repository.loadBeerList()
}