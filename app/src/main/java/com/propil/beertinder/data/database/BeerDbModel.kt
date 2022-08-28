package com.propil.beertinder.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beers")
data class BeerDbModel(
    @PrimaryKey
    val id: Long,
    val name: String?,
    val tagline: String?,
    val description: String?,
    val imageUrl: String?,
    val abv: Double?,
    val foodPairing: List<String>?,
)