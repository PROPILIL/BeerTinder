package com.propil.beertinder.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Volume(
    val value: Double,
    val unit: String
)