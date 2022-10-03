package com.propil.beertinder.data.mapper

import com.propil.beertinder.data.database.BeerDbModel
import com.propil.beertinder.data.remote.model.BeerDto
import com.propil.beertinder.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.Response
import javax.inject.Inject

class BeerMapper @Inject constructor() {


    fun mapDbModelToEntity(beerDbModel: BeerDbModel) = Beer(
        id = beerDbModel.id,
        name = beerDbModel.name,
        tagline = beerDbModel.tagline,
        description = beerDbModel.description,
        imageUrl = beerDbModel.imageUrl,
        abv = beerDbModel.abv,
        foodPairing = beerDbModel.foodPairing,
        favorite = beerDbModel.favorite
    )

    fun mapEntityToDbModel(beer: Beer) = BeerDbModel(
        id = beer.id,
        name = beer.name,
        tagline = beer.tagline,
        description = beer.description,
        imageUrl = beer.imageUrl,
        abv = beer.abv,
        foodPairing = beer.foodPairing,
        favorite = beer.favorite
    )

    fun mapDtoToEntity(beerDto: BeerDto) = Beer(
        id = beerDto.id,
        name = beerDto.name,
        tagline = beerDto.tagline,
        description = beerDto.description,
        imageUrl = beerDto.imageUrl,
        abv = beerDto.abv,
        foodPairing = beerDto.foodPairing
    )

    fun mapResponseToEntity(response: Response<List<BeerDto>>): Beer {
        return response.body()?.let { mapDtoToEntity(it[0]) }!!
    }

    fun mapResponseRandomToEntity(response: Response<List<BeerDto>>):List<Beer>{
        val mappedResponse = response.body()?.map { beerDto ->
            mapDtoToEntity(beerDto)
        }!!
        return mappedResponse
    }
}