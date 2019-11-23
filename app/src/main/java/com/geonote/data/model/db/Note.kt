package com.geonote.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var title: String,
    var description: String,
    @ColumnInfo(name = "thumbnail_uri")
    var thumbnailUri: String,
    var longitude: Double,
    var latitude: Double,
    @ColumnInfo(name = "radius_m")
    var radiusM: Int,
    var dateFrom: Long,
    var dateTo: Long,
    val color: Int
): Serializable