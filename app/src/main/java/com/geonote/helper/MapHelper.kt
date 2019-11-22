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
class MapHelper(
    private val mMap: GoogleMap,
    context: Context,
    private var mCallback: Callback? = null
) {

    private var mMarkerDataList = mutableMapOf<Marker, com.geonote.data.model.Marker>()

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
        addOnMarkerDragListener()
    }

    private fun addOnMarkerDragListener() {
        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {}
            override fun onMarkerDrag(marker: Marker?) {}

            override fun onMarkerDragEnd(marker: Marker) {
                val markerData = mMarkerDataList[marker] ?: return
                markerData.run {
                    longitude = marker.position.longitude
                    latitude = marker.position.latitude
                }
                mCallback?.onMarkerPositionChanged(markerData)
            }
        })
    }

    fun placeMarkers(markerDataList: List<com.geonote.data.model.Marker>) {
        for (markerData in markerDataList) {
            mMarkerDataList[addMarker(markerData)] = markerData
        }
    }

    private fun addMarker(marker: com.geonote.data.model.Marker) =
        addMarker(marker.id.toString(), marker.latitude, marker.longitude)

    private fun addMarker(sid: String, latitude: Double, longitude: Double) =
        mMap.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .draggable(true)
        )

    companion object {
        private val MAP_PADDING_TOP = 40.toPixels()

        private const val DEFAULT_LONGITUDE = 53.904183
        private const val DEFAULT_LATITUDE = 27.560866
        private const val DEFAULT_ZOOM = 11F

        interface Callback {
            fun onMarkerPositionChanged(markerData: com.geonote.data.model.Marker)
        }
    }
}