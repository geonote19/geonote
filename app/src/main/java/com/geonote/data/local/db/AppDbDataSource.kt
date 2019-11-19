package com.geonote.data.local.db

import android.content.Context
import com.geonote.data.model.db.Note

class AppDbDataSource private constructor(private val mDataBase: AppDataBase) {

    fun addOrUpdateNote(note: Note) {
        mDataBase.noteDao().insertNote(note)
    }

    companion object {
        private var INSTANCE: AppDbDataSource? = null

        fun getInstance(context: Context): AppDbDataSource {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = AppDbDataSource(AppDataBase.getInstance(context))
                    }
                }
            }
            return INSTANCE!!
        }
    }
}