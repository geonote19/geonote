package com.geonote.data.model.db

import androidx.room.PrimaryKey

data class Marker(
    @PrimaryKey
    val id: Int,
    val longitude: Double,
    val latitude: Double,
    val radiusM: Int
)