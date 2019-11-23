package com.geonote.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import com.geonote.R
import com.geonote.utils.ViewUtils
import com.geonote.utils.toPixels

class ColoredTextView : FrameLayout {

    private lateinit var mTextView: TextView

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

    fun setText(text: String) {
        mTextView.text = text.toUpperCase()
    }

    fun setColor(color: Int) {
        background.colorFilter = ViewUtils.getColorFilter(color)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        isClickable = true
        setBackgroundResource(R.drawable.colored_text_view_gradient)

        mTextView = TextView(context)
            .apply {
                setPadding(15.toPixels(), 0, 10.toPixels(), 0)
                setBackgroundResource(R.drawable.colored_text_view_text_background)
                setTextColor(Color.BLACK)
                textSize = 25F
                gravity = Gravity.CENTER_VERTICAL
            }
        val params = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params.leftMargin = 1.toPixels()
        params.topMargin = 1.toPixels()
        params.bottomMargin = 1.toPixels()
        addView(mTextView, params)
    }
}