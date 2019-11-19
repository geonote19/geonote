package com.geonote.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.geonote.data.local.db.dao.NoteDao
import com.geonote.data.model.db.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: AppDataBase? = null
        private const val DATABASE_NAME = "geonote.db"

        fun getInstance(context: Context): AppDataBase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}