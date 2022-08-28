package com.propil.beertinder.domain.logic

import androidx.lifecycle.LiveData
import com.propil.beertinder.domain.model.Beer

class GetBeerListUseCase(
    private val repository: BeerRepository
) {
    operator fun invoke(): LiveData<List<Beer>> {
        return repository.getBeerList()
    }
}