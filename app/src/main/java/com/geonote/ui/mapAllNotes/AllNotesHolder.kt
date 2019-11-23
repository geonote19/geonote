package com.geonote.ui.mapAllNotes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.geonote.R
import com.geonote.data.model.db.Note
import com.geonote.views.ColoredTextView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_one_note.*

class AllNotesHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
    override val containerView: View?
        get() = itemView

    val coloredTextView = itemView.findViewById<ColoredTextView>(R.id.coloredTextView)

    fun bind(note: Note) {
        coloredTextView.apply {
            setText(note.title)
            setColor(note.color)
        }
    }
}