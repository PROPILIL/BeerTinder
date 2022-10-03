package com.propil.beertinder.domain.logic

import com.propil.beertinder.domain.model.Beer
import javax.inject.Inject

class LoadRandomBeersUseCase @Inject constructor(
    val repository: BeerRepository
) {
    suspend operator fun invoke() = repository.loadRandomBeers()

}