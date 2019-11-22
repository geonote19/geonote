package com.geonote.map

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback

class CustomMapView : MapView, LifecycleObserver {

    private var googleMap: GoogleMap? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, options: GoogleMapOptions?) : super(context, options)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStarted() {
        onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumed() {
        onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPaused() {
        onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopped() {
        onStop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyed() {
        onDestroy()
    }

    override fun getMapAsync(callback: OnMapReadyCallback?) {
        super.getMapAsync {
            with(it) {
                googleMap = this
                with(uiSettings) {
                    isCompassEnabled = false
                    isZoomControlsEnabled = false
                    isRotateGesturesEnabled = false
                    isMyLocationButtonEnabled = false
                    isTiltGesturesEnabled = false
                    isMapToolbarEnabled = false
                }
            }
            callback?.onMapReady(it)

        }
    }

    fun setMapPaddings(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
        googleMap?.setPadding(left, top, right, bottom)
    }
}
