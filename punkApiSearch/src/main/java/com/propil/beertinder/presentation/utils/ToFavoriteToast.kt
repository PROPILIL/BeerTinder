package com.propil.beertinder.presentation.utils

import android.content.Context
import android.widget.Toast

object ToFavoriteToast {
    fun show(context: Context,message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}