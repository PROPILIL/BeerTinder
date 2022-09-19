package com.propil.beertinder.presentation.utils

import android.widget.ImageView
import coil.load
import com.propil.beertinder.R

object CoilImageLoader {

    fun loadImageWithCoil(imageView: ImageView, url: String?) {
        imageView.load(url) {
            crossfade(true)
            placeholder(R.drawable.beer_mug)
            error(R.drawable.broken_beer)
        }
    }

}