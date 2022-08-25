package com.propil.beertinder.domain.model

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class Ingredients(
    val malt: List<Malt>?,
    val hops: List<Hops>?,
    val yeast: String?
)

@Serializable
data class Hops(
    val name: String?,
    @Embedded
    val amount: HopsAmount,
    val add: String?,
    val attribute: String?,
)

@Serializable
data class Malt(
    val name: String?,
    @Embedded
    val amount: MaltAmount
)

@Serializable
data class HopsAmount(
    val value: Double?,
    val unit: String?
)

@Serializable
data class MaltAmount(
    val value: Double?,
    val unit: String?
)