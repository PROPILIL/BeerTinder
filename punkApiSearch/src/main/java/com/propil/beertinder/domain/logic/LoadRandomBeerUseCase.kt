package com.propil.beertinder.domain.logic

import com.propil.beertinder.domain.model.Beer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LoadRandomBeerUseCase @Inject constructor(
    private val repository: BeerRepository
) {

    suspend operator fun invoke(): Beer {
        return repository.loadRandomBeer()
    }
}