package com.geonote.core

import com.geonote.data.model.Marker

abstract class AbstractGeoManager {

    abstract fun addOrUpdateMarker(marker: Marker)

    abstract fun removeMarker(marker: Marker)
}