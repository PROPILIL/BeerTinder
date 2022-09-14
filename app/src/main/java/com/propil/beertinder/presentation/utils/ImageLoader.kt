package com.propil.beertinder.presentation.utils

import android.widget.ImageView
import coil.load
import com.propil.beertinder.R

abstract class ImageLoader {

    companion object{
        fun loadImageWithCoil(imageView: ImageView, url: String?) {
            imageView.load(url) {
                crossfade(true)
                placeholder(R.drawable.beer_mug)
                error(R.drawable.broken_beer)
            }
        }
    }
   abstract fun loadImage(imageView: ImageView, url: String?)

}