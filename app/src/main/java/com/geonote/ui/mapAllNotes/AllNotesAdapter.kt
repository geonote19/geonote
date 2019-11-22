package com.geonote.ui.mapAllNotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geonote.R
import com.geonote.data.model.db.Note

class AllNotesAdapter(private var notes: MutableList<Note>, private val listener: ClickListener) :
    RecyclerView.Adapter<AllNotesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNotesHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_one_note, parent, false)

        val holder = AllNotesHolder(view)
        holder.itemView.setOnClickListener {
            listener.onClickNote(notes[holder.adapterPosition])
        }
        return holder
    }

    fun setData(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AllNotesHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface ClickListener {
        fun onClickNote(item: Note)
    }
}