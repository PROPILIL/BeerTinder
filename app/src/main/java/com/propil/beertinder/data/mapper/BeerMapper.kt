package com.propil.beertinder.data.mapper

import android.app.Application
import coil.request.ImageRequest
import com.propil.beertinder.data.database.BeerDbModel
import com.propil.beertinder.data.remote.model.BeerDto
import com.propil.beertinder.domain.model.Beer
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
        foodPairing = beerDbModel.foodPairing
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

}