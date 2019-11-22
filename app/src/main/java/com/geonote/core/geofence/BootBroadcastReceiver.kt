package com.geonote.core.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.geonote.data.AppRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Timber.d("Restore markers after boot")
        RestoreJobIntentService.enqueueWork(context)
    }
}