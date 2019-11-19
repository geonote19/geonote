package com.geonote.core

import android.app.Service
import android.content.Intent
import com.geonote.data.AppRepository

class GeoService : Service() {

    private val mAppRepository by lazy { AppRepository.getInstance(this) }

    override fun onBind(intent: Intent?) = null

    override fun onCreate() {
        isServiceRunning = true
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        var isServiceRunning: Boolean = false
            private set
    }
}