package com.propil.beertinder.data.mapper

import com.propil.beertinder.data.database.BeerDbModel
import com.propil.beertinder.data.remote.BeerDto

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


}