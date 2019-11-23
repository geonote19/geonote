package com.geonote.data

import android.content.Context
import com.geonote.core.geofence.GeofenceManager
import com.geonote.data.local.AppPrefDataSource
import com.geonote.data.local.db.AppDataBase
import com.geonote.data.model.Marker
import com.geonote.data.model.db.Note
import com.geonote.data.model.toMarker
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppRepository private constructor(
    private val mPrefDataSource: AppPrefDataSource,
    private val mDataBase: AppDataBase,
    private val mGeoManager: GeofenceManager
) {

    suspend fun updateMarkerLocation(marker: Marker): Unit = suspendCoroutine {
        mDataBase.noteDao().run {
            val note = getNoteById(marker.id)
                .apply {
                    this.latitude = marker.latitude
                    this.latitude = marker.latitude
                    this.longitude = marker.longitude
                    this.dateTo = marker.dateTo
                    this.dateFrom = marker.dateFrom
                    this.title = marker.title
                    this.radiusM = marker.radiusM
                }
            insertNote(note)
            mGeoManager.addOrUpdateMarker(marker)
            it.resume(Unit)
        }
    }

    suspend fun addNote(note: Note): Long = suspendCoroutine {
        mDataBase.noteDao().run {
            val insertedId =insertNote(note)
            mGeoManager.addOrUpdateMarker(note.toMarker())
            it.resume(insertedId)
        }
    }

    /**
     * Run in the thread background
     */
    fun restoreMarkers() {
        val markerList = mDataBase.noteDao().getAllNotes()
            .map { it.toMarker() }
        for (marker in markerList) {
            mGeoManager.addOrUpdateMarker(marker)
        }
    }

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

    suspend fun removeNote(noteIds: List<Long>): Unit =
        suspendCoroutine {
            mDataBase.noteDao().removeNoteByIds(noteIds.map { it.toString() })
            it.resume(Unit)
        }

    suspend fun mergeNote(note: Note): Unit =
        suspendCoroutine {
            mDataBase.noteDao().insertNote(note)
            it.resume(Unit)
        }

    companion object {
        private var INSTANCE: AppRepository? = null

        fun getInstance(context: Context): AppRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = AppRepository(
                            AppPrefDataSource.getInstance(context),
                            AppDataBase.getInstance(context),
                            GeofenceManager.getInstance(context)
                        )
                    }
                }
            }
            return INSTANCE!!
        }
    }
}