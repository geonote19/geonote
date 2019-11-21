package com.geonote.data

import android.content.Context
import com.geonote.core.GeoManager
import com.geonote.data.local.AppPrefDataSource
import com.geonote.data.local.db.AppDataBase
import com.geonote.data.model.db.Note
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppRepository private constructor(
    private val mPrefDataSource: AppPrefDataSource,
    private val mDataBase: AppDataBase
) {

    suspend fun getNoteList(): List<Note> =
        suspendCoroutine {
            val noteList = mDataBase.noteDao().getAllNotes()
            it.resume(noteList)
        }

    suspend fun getNoteById(id: Long): Note =
        suspendCoroutine {
            val note = mDataBase.noteDao().getNoteById(id)
            it.resume(note)
        }

    companion object {
        private var INSTANCE: AppRepository? = null

        fun getInstance(context: Context): AppRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = AppRepository(
                            AppPrefDataSource.getInstance(context),
                            AppDataBase.getInstance(context)
                        )
                    }
                }
            }
            return INSTANCE!!
        }
    }
}