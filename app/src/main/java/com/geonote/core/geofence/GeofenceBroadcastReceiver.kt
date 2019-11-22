package com.geonote.core.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.geonote.data.AppRepository
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
       GeofenceManager.handleOnReceive(intent) { markerIds ->
           GlobalScope.launch {
               val appRepository = AppRepository.getInstance(context)
               appRepository.removeNote(markerIds.map { it.toLong() })
           }
       }
    }
}