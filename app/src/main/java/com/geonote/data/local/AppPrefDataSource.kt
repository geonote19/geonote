package com.geonote.data.local

import android.content.Context
import androidx.core.content.edit

class AppPrefDataSource private constructor(context: Context) {

    private val mPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // Add save/is/get methods here

    companion object {
        private var INSTANCE: AppPrefDataSource? = null
        private const val PREF_NAME = "geonote_pref"

        fun getInstance(context: Context): AppPrefDataSource {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = AppPrefDataSource(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}