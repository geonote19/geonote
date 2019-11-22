package com.geonote.helper

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import com.geonote.R
import com.geonote.utils.SystemUtils
import com.geonote.utils.toPixels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

@SuppressLint("MissingPermission")
class MapHelper(private val mMap: GoogleMap, context: Context) {

    private var mMarkerList = mutableListOf<Marker>()

    init {
        with(mMap) {
            setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style))
            moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE),
                    DEFAULT_ZOOM
                )
            )
            if (SystemUtils.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                isMyLocationEnabled = true
            }
            setPadding(0, MAP_PADDING_TOP, 0, 0)
        }
    }

    fun placeMarkers(markerList: List<com.geonote.data.model.Marker>) {
        for (marker in markerList) {
            addMarker(marker)
        }
    }

    private fun addMarker(marker: com.geonote.data.model.Marker) =
        addMarker(marker.id.toString(), marker.latitude, marker.longitude)

    private fun addMarker(sid: String, latitude: Double, longitude: Double) =
        mMap.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .title(sid)
        )

    companion object {
        private val MAP_PADDING_TOP = 40.toPixels()

        private const val DEFAULT_LATITUDE = 53.904183
        private const val DEFAULT_LONGITUDE = 27.560866
        private const val DEFAULT_ZOOM = 11F
    }
}