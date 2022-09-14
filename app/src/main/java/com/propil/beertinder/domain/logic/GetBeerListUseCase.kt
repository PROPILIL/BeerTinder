package com.propil.beertinder.domain.logic

import androidx.lifecycle.LiveData
import com.propil.beertinder.domain.model.Beer
import javax.inject.Inject

class GetBeerListUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    operator fun invoke(): LiveData<List<Beer>> {
        return repository.getBeerList()
    }
}