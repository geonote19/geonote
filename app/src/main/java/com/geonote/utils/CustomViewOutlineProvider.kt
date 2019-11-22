package com.geonote.utils

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.geonote.App
import com.geonote.R

class CustomViewOutlineProvider(private val mRoundedArea: RoundedArea) : ViewOutlineProvider() {

    private val mRadius: Int =
        App.INSTANCE.resources.getDimensionPixelSize(R.dimen.default_view_corner_radius)

    override fun getOutline(view: View, outline: Outline) {
        val extraTopSpace: Int
        val extraBottomSpace: Int
        when (mRoundedArea) {
            RoundedArea.TOP -> {
                extraTopSpace = 0
                extraBottomSpace = mRadius
            }
            RoundedArea.BOTTOM -> {
                extraTopSpace = mRadius
                extraBottomSpace = 0
            }
            else -> {
                extraTopSpace = 0
                extraBottomSpace = 0
            }
        }
        outline.setRoundRect(
            0,
            0 - extraTopSpace,
            view.width,
            view.height + extraBottomSpace,
            mRadius.toFloat()
        )
    }

    enum class RoundedArea {
        TOP,
        BOTTOM,
        DEFAULT
    }
}

