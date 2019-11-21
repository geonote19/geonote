package com.geonote.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import timber.log.Timber

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("onReceive")
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            Timber.d("hasError: error=${geofencingEvent.errorCode}")
        }

        val geofenceTransitionType: Int = geofencingEvent.geofenceTransition

        if (geofenceTransitionType == Geofence.GEOFENCE_TRANSITION_ENTER ||
            geofenceTransitionType == Geofence.GEOFENCE_TRANSITION_EXIT
        ) {
            val geofenceList = geofencingEvent.triggeringGeofences
            for (geofence in geofenceList) {
                Timber.d("geofence name=${geofence.requestId}, geofenceTransitionType=${geofenceTransitionType}")
            }
        } else {
            Timber.d("error geofence type")
        }
    }
}