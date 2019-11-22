package com.geonote.utils

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.geonote.App

object SystemUtils {
    fun hasPermission(permission: String) =
        ContextCompat.checkSelfPermission(App.INSTANCE, permission) == PackageManager.PERMISSION_GRANTED
}