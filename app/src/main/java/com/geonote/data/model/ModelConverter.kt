package com.geonote.data.model

import com.geonote.data.model.db.Note

fun Note.toMarker() =
    Marker(id, longitude, latitude, radiusM, dateFrom, dateTo, title, color)