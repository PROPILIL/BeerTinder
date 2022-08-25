package com.propil.beertinder.domain.model

import androidx.room.Embedded
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Method(
    @JsonNames("mash_temp")
    val mashTemp: List<MashTemp>?,
    @Embedded
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