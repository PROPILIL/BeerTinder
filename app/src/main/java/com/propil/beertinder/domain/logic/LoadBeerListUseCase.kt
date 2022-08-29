package com.propil.beertinder.domain.logic

class LoadBeerListUseCase(
    private val repository: BeerRepository
) {
    suspend operator fun invoke(page: Int, per_page: Int) = repository.loadBeerList(page, per_page)
}