package com.geonote.utils

import androidx.annotation.StringRes
import com.geonote.App

object RUtils {
    fun getString(@StringRes resId: Int) = App.INSTANCE.resources.getString(resId)
}