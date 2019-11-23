package com.geonote

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import com.geonote.data.model.db.Note
import java.lang.ref.WeakReference

class CustomDialog(activity: Activity, val note: Note) {

    private val activityRef = WeakReference(activity)

    lateinit var dialog: Dialog
    lateinit var closeDialogImageView: ImageView
    lateinit var titleNoteDialog: TextView
    lateinit var imageCustomDialog: ImageView
    lateinit var detailsTextViewNewNote: TextView

    init {
        showDialog()
    }

    fun showDialog() {
        val activity = getActivity() ?: return
        dialog = Dialog(activity)
        dialog.setContentView(R.layout.custom_dialog_note)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        closeDialogImageView = dialog.findViewById(R.id.closeDialogImageView)
        closeDialogImageView.setOnClickListener { dialog.cancel() }
        titleNoteDialog = dialog.findViewById(R.id.titleNoteDialog)
        imageCustomDialog = dialog.findViewById(R.id.imageCustomDialog)
        detailsTextViewNewNote = dialog.findViewById(R.id.detailsEditTextNewNote)
        titleNoteDialog.text = note.title
        detailsTextViewNewNote.text = note.description
        imageCustomDialog.load(note.thumbnailUri)
        dialog.show()
    }

    private fun getActivity(): Activity? {
        val activity = activityRef.get()
        return if (activity?.isFinishing == false) activity else null
    }

}