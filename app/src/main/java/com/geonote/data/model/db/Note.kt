package com.geonote.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    @ColumnInfo(name = "thumbnail_uri")
    val thumbnailUri: String,
    var longitude: Double,
    var latitude: Double,
    @ColumnInfo(name = "radius_m")
    var radiusM: Int,
    var lifetimeMs: Long
)