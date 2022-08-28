package com.propil.beertinder.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class BoilVolumeDto(
    val value: Double,
    val unit: String
)