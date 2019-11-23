package com.geonote.utils

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter

object ViewUtils {
    fun getColorFilter(color: Int) =
        PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY)
}