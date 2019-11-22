package com.geonote.ui.mapAllNotes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.geonote.data.model.db.Note
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_one_note.*

class AllNotesHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
    override val containerView: View?
        get() = itemView

    fun bind(note: Note) {
        itemAlliNotesText.text = note.title
    }
}