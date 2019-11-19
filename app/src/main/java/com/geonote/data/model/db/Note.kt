package com.geonote.data.model.db

import androidx.room.Entity

@Entity
data class Note(
    val marker: Marker,
    val title: String,
    val description: String,
    val thumbnailUri: String
)