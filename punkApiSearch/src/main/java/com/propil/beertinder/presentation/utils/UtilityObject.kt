package com.propil.beertinder.presentation.utils

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat
import coil.load
import com.google.android.material.chip.Chip
import com.propil.beertinder.R

object UtilityObject {

    fun loadImageWithCoil(imageView: ImageView, url: String?) {
        imageView.load(url) {
            crossfade(true)
            placeholder(R.drawable.beer_mug)
            error(R.drawable.broken_beer)
        }
    }

}

fun main(){

    val list = listOf("Spicy carne asada with a pico de gallo sauce",
        "Shredded chicken tacos with a mango chilli lime salsa",
        "Cheesecake with a passion fruit swirl sauce")

    val string = list.joinToString()

    print(string)
}