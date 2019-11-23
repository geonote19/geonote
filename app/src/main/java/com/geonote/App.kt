package com.geonote

import android.app.Application
import android.os.Handler
import com.geonote.ui.notification.NotifManager
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        handler = Handler()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        NotifManager.getInstance().createNotificationChannel()
    }

    companion object {
        lateinit var INSTANCE: App
        lateinit var handler: Handler
    }
}