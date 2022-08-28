package com.propil.beertinder.data.remote

import androidx.room.Embedded
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class BeerDto(
    val id: Long,
    val name: String?,
    val tagline: String?,
    val firstBrewed: String?,
    val description: String?,
    val imageUrl: String?,
    val abv: Double?,
    val ibu: Double?,
    @JsonNames("target_fg")
    val targetFg: Double?,
    @JsonNames("target_og")
    val targetOg: Double?,
    val ebc: Double?,
    val srm: Double?,
    val ph: Double?,
    @JsonNames("attenuation_level")
    val attenuationLevel: Double?,
    val volumeDto: VolumeDto?,
    @JsonNames("boil_volume")
    val boilVolumeDto: BoilVolumeDto?,
    @Embedded
    val method: Method?,
    @Embedded
    val ingredients: Ingredients?,
    @Embedded
    val hops: Hops?,
    val yeast: String?,
    @JsonNames("food_pairing")
    val foodPairing: List<String>?,
    @JsonNames("brewers_tips")
    val brewersTips: String?,
    @JsonNames("contributed_by")
    val contributedBy: String?
)