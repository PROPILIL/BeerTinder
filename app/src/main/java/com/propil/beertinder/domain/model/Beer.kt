package com.propil.beertinder.domain.model

data class Beer (
    val id: Long,
    val name: String?,
    val tagline: String?,
    val description: String?,
    val imageUrl: String?,
    val abv: Double?,
    val foodPairing: List<String>?,
        )