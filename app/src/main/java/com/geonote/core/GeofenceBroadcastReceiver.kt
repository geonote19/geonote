package com.geonote.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.geonote.helper.K
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        K.d("onReceive")
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            K.d("hasError: error=${geofencingEvent.errorCode}")
        }

        val geofenceTransitionType: Int = geofencingEvent.geofenceTransition

        if (geofenceTransitionType == Geofence.GEOFENCE_TRANSITION_ENTER ||
            geofenceTransitionType == Geofence.GEOFENCE_TRANSITION_EXIT
        ) {
            val geofenceList = geofencingEvent.triggeringGeofences
            for (geofence in geofenceList) {
                K.d("geofence name=${geofence.requestId}, geofenceTransitionType=${geofenceTransitionType}")
            }
        } else {
            K.d("error geofence type")
        }
    }
}