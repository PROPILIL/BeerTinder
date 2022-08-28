package com.propil.beertinder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.propil.beertinder.network.PunkApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BeerListViewModel : ViewModel() {

    private val _requestStatus = MutableLiveData<String>()
    val requestStatus: LiveData<String>
        get() = _requestStatus

    init {
        getBeerList()
    }

    private fun getBeerList() {
        viewModelScope.launch {
            val resultList = getBeersFromApi()
            try {
                var result = resultList.
            }

        }
    }

    suspend fun getBeersFromApi() = withContext(Dispatchers.IO) {
        PunkApiFactory.punkApi.getBeers()
    }
}