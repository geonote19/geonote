package com.geonote.data

import android.content.Context
import com.geonote.core.GeoManager
import com.geonote.data.local.AppPrefDataSource
import com.geonote.data.local.db.AppDbDataSource
import com.geonote.data.model.db.Note

class AppRepository private constructor(
    private val mPrefDataSource: AppPrefDataSource,
    private val mDbDataSource: AppDbDataSource,
    private val mGeoManager: GeoManager
) {

    fun addNote(note: Note) {
        mDbDataSource.addOrUpdateNote(note)
        mGeoManager.addMarker(note.marker)
    }

    // ...

    companion object {
        private var INSTANCE: AppRepository? = null

        fun getInstance(context: Context): AppRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = AppRepository(
                            AppPrefDataSource.getInstance(context),
                            AppDbDataSource.getInstance(context),
                            GeoManager.getInstance(context)
                        )
                    }
                }
            }
            return INSTANCE!!
        }
    }
}