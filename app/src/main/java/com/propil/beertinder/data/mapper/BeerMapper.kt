package com.propil.beertinder.data.mapper

import com.propil.beertinder.data.database.BeerDbModel
import com.propil.beertinder.data.remote.model.BeerDto
import com.propil.beertinder.domain.model.Beer
import retrofit2.Response

class BeerMapper {

//    //convert dto to dbModel
//    fun mapDtoToDbModel(beerDto: BeerDto) = beerDto[0].let {
//        BeerDbModel(
//            id = it.id,
//            name = it.name,
//            tagline = it.tagline,
//            description = it.description,
//            imageUrl = it.imageUrl,
//            abv = it.abv,
//            foodPairing = it.foodPairing
//        )
//    }

    fun mapDbModelToEntity(beerDbModel: BeerDbModel) = Beer(
        id = beerDbModel.id,
        name = beerDbModel.name,
        tagline = beerDbModel.tagline,
        description = beerDbModel.description,
        imageUrl = beerDbModel.imageUrl,
        abv = beerDbModel.abv,
        foodPairing = beerDbModel.foodPairing
    )

    fun mapEntityToDbModel(beer: Beer) = BeerDbModel (
        id = beer.id,
        name = beer.name,
        tagline = beer.tagline,
        description = beer.description,
        imageUrl = beer.imageUrl,
        abv = beer.abv,
        foodPairing = beer.foodPairing
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

    fun mapResponseToEntity(response: Response<List<BeerDto>>) : Beer {
        return response.body()?.let { mapDtoToEntity(it[0]) }!!
    }

    fun mapResponseListToEntityList(response: Response<List<BeerDto>>): List<Beer> {
        val beerList = mutableListOf<Beer>()
        for (item in response.body()!!) {
            var items = mapDtoToEntity(item)
            beerList.add(items)
        }
        return beerList
    }
}