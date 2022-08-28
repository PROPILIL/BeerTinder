package com.propil.beertinder.data.remote

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Method(
    @JsonNames("mash_temp")
    val mashTemp: List<MashTemp>?,
    val fermentation: Fermentation?,
    val twist: String?
)

@Serializable
data class MashTemp(
    val temp: Double?,
    val unit: String?
)

@Serializable
data class Fermentation(
    val temp: FermentationTemp,
    val twist: String?
)

@Serializable
data class FermentationTemp(
    val value: Double?,
    val unit: String?
)