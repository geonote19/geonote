package com.geonote

import android.app.Application
import com.geonote.ui.notification.NotifManager
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        NotifManager.getInstance().createNotificationChannel()
    }

    companion object {
        lateinit var INSTANCE: App
    }
}