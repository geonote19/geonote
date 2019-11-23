package com.geonote.core.geofence

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.geonote.core.AbstractGeoManager
import com.geonote.data.model.Marker
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import timber.log.Timber
import java.util.*

class GeofenceManager private constructor(context: Context) : AbstractGeoManager() {

    private val mGeofencingClient = LocationServices.getGeofencingClient(context)

    private val geofencePendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun addOrUpdateMarker(marker: Marker) {
        val geofence = Geofence.Builder()
            .setRequestId(marker.id.toString())
            .setCircularRegion(marker.latitude, marker.longitude, marker.radiusM.toFloat())
            .setExpirationDuration(marker.dateTo)
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER
                        or Geofence.GEOFENCE_TRANSITION_EXIT
            )
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        mGeofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
            addOnSuccessListener {
                Timber.d("Marker was successfully added")
            }
            addOnFailureListener {
                Timber.e(it, "Error during adding a marker")
            }
        }
    }

    override fun removeMarker(marker: Marker) {
        mGeofencingClient.removeGeofences(listOf(marker.id.toString())).run {
            addOnSuccessListener {
                Timber.d("Marker was successfully added")
            }
            addOnFailureListener {
                Timber.e(it, "Error during removing a marker")
            }
        }
    }

    companion object {
        private var INSTANCE: GeofenceManager? = null

        fun getInstance(context: Context): GeofenceManager {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            GeofenceManager(context)
                    }
                }
            }
            return INSTANCE!!
        }

        fun handleOnReceive(intent: Intent?, response: (markerIds: List<Int>) -> Unit) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            if (geofencingEvent.hasError()) {
                Timber.d("Error during getting nearest notes: error=${geofencingEvent.errorCode}")
            }
            val geofenceTransitionType: Int = geofencingEvent.geofenceTransition
            if (geofenceTransitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
                val geofenceList = geofencingEvent.triggeringGeofences
                val noteIds = geofenceList.map { it.requestId.toInt() }
                Timber.d("Nearest notes were founded with ids=${noteIds}")
                response(noteIds)
            } else {
                Timber.d("Nearest notes were received, but with error: type = $geofenceTransitionType")
            }
        }
    }

}