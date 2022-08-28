package com.propil.beertinder.domain.logic

class LoadBeerListUseCase(
    private val repository: BeerRepository
) {
    suspend operator fun invoke() = repository.loadBeerList()
}