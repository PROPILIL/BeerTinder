package com.propil.beertinder.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.propil.beertinder.data.remote.model.BeerDto
import com.propil.beertinder.data.remote.network.PunkApiService
import retrofit2.HttpException
import java.lang.Exception

private const val PUNK_API_STARTING_PAGE_INDEX = 1

class PunkApiPagingSource(
    private val service: PunkApiService,
) : PagingSource<Int, BeerDto>() {

  override fun getRefreshKey(state: PagingState<Int, BeerDto>): Int = 1
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerDto> {
        try {
            val page = params.key ?: PUNK_API_STARTING_PAGE_INDEX
            val response = service.loadBeerList(page, params.loadSize)

            if (response.isSuccessful) {
                val beers = checkNotNull(response.body())
                val prevKey = if (page == PUNK_API_STARTING_PAGE_INDEX) null else page - 1
                return LoadResult.Page(
                    data = beers,
                    prevKey = prevKey,
                    nextKey = page.plus(1)
                )
            } else {
                return LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}