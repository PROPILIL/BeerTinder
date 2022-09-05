package com.propil.beertinder.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.propil.beertinder.data.remote.model.BeerDto
import com.propil.beertinder.data.remote.network.PunkApiService
import java.lang.Exception

private const val PUNK_API_STARTING_PAGE_INDEX = 1

class PunkApiPagingSource(
    private val service: PunkApiService,
) : PagingSource<Int, BeerDto>() {

    override fun getRefreshKey(state: PagingState<Int, BeerDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerDto> {
        try {
            val position = params.key ?: PUNK_API_STARTING_PAGE_INDEX
            val response = service.loadBeerList(position, params.loadSize)
            val responseData = mutableListOf<BeerDto>()
            for (items in response.body()!!) {
                responseData.add(items)
            }

            val prevKey = if (position == PUNK_API_STARTING_PAGE_INDEX) null
            else position - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = position.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}