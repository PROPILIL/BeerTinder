package com.propil.beertinder.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class VolumeDto(
    val value: Double,
    val unit: String
)