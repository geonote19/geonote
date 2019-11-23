package com.geonote.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.geonote.R
import com.geonote.utils.ViewUtils
import timber.log.Timber

class ChooseColorView : LinearLayout {

    private val mColorList = mutableListOf<Int>()
    private var mRadius: Int = 0
    private var mListener: OnColorClickedListener? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    fun setColorClickedListener(listener: OnColorClickedListener) {
        mListener = listener
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        orientation = HORIZONTAL
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.ChooseColorView)
            try {
                val resId = a.getResourceId(R.styleable.ChooseColorView_color_array, 0)
                val colors = context.resources.getStringArray(resId)
                for (color in colors) {
                    mColorList.add(Color.parseColor(color))
                }
                mRadius = a.getDimensionPixelOffset(R.styleable.ChooseColorView_radius, 0)
                Timber.d("Colors was loaded: colors = $mColorList")
            } finally {
                a.recycle()
            }
        }
        for (color in mColorList) {
            addView(createViewWithShadow(color))
        }
    }

    private fun createViewWithShadow(color: Int): View {
        val parent = FrameLayout(context)
            .apply {
                layoutParams = LayoutParams(0, mRadius * 2)
                    .apply { weight = 1F }
            }
        val imageView = ImageView(context)
            .apply {
                setImageResource(R.drawable.choose_color_view_circle)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                colorFilter = ViewUtils.getColorFilter(color)
                layoutParams = FrameLayout.LayoutParams(mRadius, mRadius)
                    .apply { gravity = Gravity.CENTER }
                setOnClickListener {
                    mListener?.onColorClicked(color)
                }
            }
        parent.addView(imageView)
        return parent
    }

    companion object {
        interface OnColorClickedListener {
            fun onColorClicked(color: Int)
        }
    }
}