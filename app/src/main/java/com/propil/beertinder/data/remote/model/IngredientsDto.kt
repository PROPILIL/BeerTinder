package com.propil.beertinder.data.remote.model

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
    val amount: HopsAmount,
    val add: String?,
    val attribute: String?,
)

@Serializable
data class Malt(
    val name: String?,
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