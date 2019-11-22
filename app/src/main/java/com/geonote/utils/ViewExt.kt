package com.geonote.utils

import android.view.View

fun View.applyDefaultOutlineProvider(roundArea: CustomViewOutlineProvider.RoundedArea) {
    this.outlineProvider = CustomViewOutlineProvider(roundArea)
    this.clipToOutline = true
}