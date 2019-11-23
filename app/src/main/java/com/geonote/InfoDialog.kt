package com.geonote

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.geonote.data.model.db.Note
import com.geonote.utils.toPixels

class InfoDialog private constructor(private val mNote: Note) : DialogFragment() {

    protected val mHeightFactor = 0.85F
    protected val mWidthFactor = 0.95F
    protected val mHeight = 0
    protected val mMaxHeight = 500F.toPixels()
    protected val mMaxWidth = 450F.toPixels()

    override fun onResume() {
        super.onResume()
        setWindowSize()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        prepareWindow()
        return inflater.inflate(R.layout.custom_dialog_note, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textTitle = view.findViewById<TextView>(R.id.titleNoteDialog)
        val textDescription = view.findViewById<TextView>(R.id.detailsEditTextNewNote)
        val buttonClose = view.findViewById<View>(R.id.closeDialogImageView)
        textTitle.text = mNote.title
        textDescription.text = mNote.description
        buttonClose.setOnClickListener {
            dismiss()
        }
    }

    private fun setWindowSize() {
        val window = dialog!!.window!!
        val params = window.attributes
        val point = Point()
        activity!!.windowManager.defaultDisplay.getSize(point)
        params.width = Math.min(point.x * mWidthFactor, mMaxWidth).toInt()
        params.height = Math.min(
            if (mHeight != 0) mHeight.toFloat() else point.y * mHeightFactor,
            mMaxHeight
        ).toInt()
        window.attributes = params
    }

    private fun prepareWindow() {
        with(dialog!!) {
            setCanceledOnTouchOutside(true)
            with(window!!) {
                requestFeature(Window.FEATURE_NO_TITLE)
                setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
    }

    companion object {
        fun newInstance(
            note: Note
        ): InfoDialog {
            return InfoDialog(note)
        }
    }

}