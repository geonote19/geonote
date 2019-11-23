package com.geonote.utils

import androidx.annotation.StringRes
import com.geonote.App
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.os.Build
import android.view.View
import android.view.Window


object RUtils {
    fun getString(@StringRes resId: Int) = App.INSTANCE.resources.getString(resId)
}

fun setSystemBarTheme(isDark: Boolean,window:Window) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decor = window.getDecorView()
        decor.setSystemUiVisibility(
            if (isDark)
                decor.getSystemUiVisibility() or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else
                decor.getSystemUiVisibility() and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        )
    }
}