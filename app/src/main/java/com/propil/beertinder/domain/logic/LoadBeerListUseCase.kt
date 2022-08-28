package com.propil.beertinder.domain.logic

class LoadBeerListUseCase(
    private val repository: BeerRepository
) {
    operator fun invoke() = repository.loadBeerList()
}