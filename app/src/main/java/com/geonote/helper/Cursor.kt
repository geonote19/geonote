package com.geonote.helper

import android.database.Cursor

inline fun <R> Cursor.useCursor(block: (Cursor) -> R): R {
    try {
        return block(this)
    } catch (e: Throwable) {
        throw e
    } finally {
        close()
    }
}
