package com.geonote.core.geofence

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.geonote.data.AppRepository

class RestoreJobIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        AppRepository.getInstance(applicationContext)
            .restoreMarkers()
    }
    companion object {
        private const val RESTORE_ID = 1

        fun enqueueWork(context: Context) {
            enqueueWork(context, RestoreJobIntentService::class.java, RESTORE_ID,
                Intent(context, RestoreJobIntentService::class.java))
        }
    }
}