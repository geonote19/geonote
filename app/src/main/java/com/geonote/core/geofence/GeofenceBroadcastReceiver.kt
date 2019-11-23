package com.geonote.core.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.geonote.data.AppRepository
import com.geonote.ui.notification.NotifManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        GeofenceManager.handleOnReceive(intent) { markerIds ->
            GlobalScope.launch {
                val appRepository = AppRepository.getInstance(context)
                for (markerId in markerIds) {
                    val note = appRepository.getNoteById(markerId.toLong())
                    NotifManager.getInstance().showNotification(note)
                }
            }
        }
    }
}