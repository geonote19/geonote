package com.geonote.data.model

data class Marker(
    val id: Long,
    var longitude: Double,
    var latitude: Double,
    val radiusM: Int,
    val dateFrom: Long,
    val dateTo: Long,
    val title: String
)