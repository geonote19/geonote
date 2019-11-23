package com.geonote.utils

import com.geonote.App

object ScreenUtils {

    private var METRICS_DENSITY: Float = 0F

    init {
        METRICS_DENSITY = App.INSTANCE.resources.displayMetrics.density
    }

    fun convertDpToPixels(value: Float) = value * METRICS_DENSITY

    fun convertDpToPixels(value: Int) = (value * METRICS_DENSITY).toInt()

    fun convertPixelsToDp(value: Int) = (value / METRICS_DENSITY).toInt()
}


fun Float.toPixels() = ScreenUtils.convertDpToPixels(this)
fun Int.toPixels() = ScreenUtils.convertDpToPixels(this)
fun Int.toDp() = ScreenUtils.convertPixelsToDp(this)
