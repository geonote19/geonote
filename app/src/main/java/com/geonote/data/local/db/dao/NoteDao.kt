package com.geonote.data.local.db.dao

import com.geonote.data.model.db.Marker
import com.geonote.data.model.db.Note

abstract class NoteDao {

    abstract fun insertNote(note: Note)

    abstract fun remoteNote(note: Note)

    abstract fun removeNoteById(id: Int)

    abstract fun updateMarker(marker: Marker)

    abstract fun getAllNotes(): List<Note>

    // ...
}