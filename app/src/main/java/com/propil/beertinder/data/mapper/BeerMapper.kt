package com.propil.beertinder.data.mapper

import com.propil.beertinder.data.database.BeerDbModel
import com.propil.beertinder.data.remote.model.BeerDto
import com.propil.beertinder.domain.model.Beer

class BeerMapper {

    //convert dto to dbModel
    fun mapDtoToDbModel(beerDto: BeerDto) =  BeerDbModel(
            id = beerDto.id,
            name = beerDto.name,
            tagline = beerDto.tagline,
            description = beerDto.description,
            imageUrl = beerDto.imageUrl,
            abv = beerDto.abv,
            foodPairing = beerDto.foodPairing
        )

    fun mapDbModelToEntity(beerDbModel: BeerDbModel) = Beer(
        id = beerDbModel.id,
        name = beerDbModel.name,
        tagline = beerDbModel.tagline,
        description = beerDbModel.description,
        imageUrl = beerDbModel.imageUrl,
        abv = beerDbModel.abv,
        foodPairing = beerDbModel.foodPairing
    )


}