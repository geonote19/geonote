package com.geonote.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.geonote.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomMarkerInfoWindowView(context: Context) : GoogleMap.InfoWindowAdapter {

    private val mMarkerView = LayoutInflater.from(context)
        .inflate(R.layout.marker_placeholder, null)

    private val mTextTitle = mMarkerView.findViewById<TextView>(R.id.textTitle)

    override fun getInfoContents(marker: Marker) = null

    override fun getInfoWindow(marker: Marker): View {
        val markerData = marker.tag as com.geonote.data.model.Marker
        mTextTitle.text = markerData.title
        return mMarkerView
    }
}