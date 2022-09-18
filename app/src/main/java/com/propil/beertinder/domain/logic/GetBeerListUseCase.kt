package com.propil.beertinder.domain.logic

import androidx.lifecycle.LiveData
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeerListUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    operator fun invoke(): Flow<List<Beer>> {
        return repository.getBeerList()
    }
}