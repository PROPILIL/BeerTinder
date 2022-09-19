package com.propil.beertinder.presentation.utils

fun <ImageView> ImageView.loadWithCoil(url: String?) {
    CoilImageLoader.loadImageWithCoil(this as android.widget.ImageView, url)
}