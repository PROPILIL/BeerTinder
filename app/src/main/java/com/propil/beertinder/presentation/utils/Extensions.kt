package com.propil.beertinder.presentation.utils

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.navigation.NavigationBarView
import com.propil.beertinder.R
import com.propil.beertinder.presentation.fragments.BeerFavoriteFragment
import com.propil.beertinder.presentation.fragments.BeerListFragment
import com.propil.beertinder.presentation.fragments.BeerTinderFragment

fun <ImageView> ImageView.loadWithCoil(url: String?) {
    UtilityObject.loadImageWithCoil(this as android.widget.ImageView, url)
}

fun Chip.changeColor(context: Context, color: Int) {
    this.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, color))
}