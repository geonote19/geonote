package com.geonote.data.local.db.dao

import androidx.room.*
import com.geonote.data.model.db.Note

@Dao
abstract class NoteDao {

    @Query("SELECT * FROM note")
    abstract fun getAllNotes(): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    abstract fun getNoteById(id: Long): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNote(note: Note)

    @Delete
    abstract fun removeNote(note: Note)

    @Query("DELETE FROM note WHERE ID = :id")
    abstract fun removeNoteById(id: Long)

    @Query("DELETE FROM note WHERE ID in (:ids)")
    abstract fun removeNoteByIds(ids: List<String>)
    // ...
}