package com.geonote.helper

import android.util.Log

object K {

    private const val LOG_TAG = "geonote_tag"

    fun d(msg: String) {
        Log.d(LOG_TAG, msg)
    }

    fun e(msg: String, throwable: Throwable) {
        Log.e(LOG_TAG, msg, throwable)
    }
}