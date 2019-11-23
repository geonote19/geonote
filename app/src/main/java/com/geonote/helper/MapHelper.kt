package com.geonote.helper

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import com.geonote.R
import com.geonote.map.CustomMarkerInfoWindowView
import com.geonote.utils.SystemUtils
import com.geonote.utils.toPixels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

@SuppressLint("MissingPermission")
class MapHelper(
    private val mMap: GoogleMap,
    context: Context,
    private var mCallback: Callback? = null
) {

    init {
        with(mMap) {
            setInfoWindowAdapter(CustomMarkerInfoWindowView(context))
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
            setOnInfoWindowClickListener {
                (it.tag as? com.geonote.data.model.Marker)?.let {
                    mCallback?.onMarkerClicked(it)
                }
            }
        }
        addOnMarkerDragListener()
    }

    private fun addOnMarkerDragListener() {
        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {}
            override fun onMarkerDrag(marker: Marker?) {}

            override fun onMarkerDragEnd(marker: Marker) {
                val markerData = marker.tag as? com.geonote.data.model.Marker ?: return
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
            addMarker(markerData).tag = markerData
        }
    }

    fun setCameraPosition(latitude: Double, longitude: Double) {
        val position = LatLng(latitude, longitude)
        val cameraUpdate = if (mMap.cameraPosition.zoom < MAP_MIN_ZOOM) {
            CameraUpdateFactory.newLatLngZoom(position, MAP_MIN_ZOOM)
        } else CameraUpdateFactory.newLatLng(position)
        mMap.animateCamera(cameraUpdate)
    }

    private fun addMarker(markerData: com.geonote.data.model.Marker) =
        mMap.addMarker(
            MarkerOptions()
                .position(LatLng(markerData.latitude, markerData.longitude))
                .title(markerData.title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                .draggable(true)
        )

    companion object {
        private const val MAP_MIN_ZOOM = 10F

        private const val DEFAULT_LONGITUDE = 53.904183
        private const val DEFAULT_LATITUDE = 27.560866
        private const val DEFAULT_ZOOM = 11F

        interface Callback {
            fun onMarkerPositionChanged(markerData: com.geonote.data.model.Marker)
            fun onMarkerClicked(markerData: com.geonote.data.model.Marker)
        }
    }
}