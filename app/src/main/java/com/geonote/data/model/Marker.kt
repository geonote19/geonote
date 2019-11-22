package com.geonote.data.model

class Marker(
    val id: Long,
    val longitude: Double,
    val latitude: Double,
    val radiusM: Int,
    val lifetimeMs: Long
)