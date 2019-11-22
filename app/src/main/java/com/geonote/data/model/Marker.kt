package com.geonote.data.model

data class Marker(
    val id: Long,
    var longitude: Double,
    var latitude: Double,
    val radiusM: Int,
    val lifetimeMs: Long,
    val title: String
)