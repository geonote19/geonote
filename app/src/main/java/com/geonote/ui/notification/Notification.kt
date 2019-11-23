package com.geonote.ui.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.geonote.App
import com.geonote.R
import com.geonote.data.model.db.Note
import com.geonote.ui.detail.DetailFragment
import com.geonote.utils.RUtils

class NotifManager private constructor(private var mContext: Context = App.INSTANCE) {

    private val notificationManager
        get() = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun getNotificationStyle(note: Note) =
        NotificationCompat.BigTextStyle()
            .setBigContentTitle(note.title)
            .bigText(note.description)
            .setSummaryText(RUtils.getString(R.string.you_are_near))

    private fun createNotification(note: Note) =
        NotificationCompat.Builder(mContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_adb_black_24dp)
            .setContentTitle(note.title)
            .setContentText(note.description)
            .setWhen(System.currentTimeMillis())
            .setShowWhen(true)
            .setStyle(getNotificationStyle(note))
            .setContentIntent(createPendingIntent(note))
            .setAutoCancel(true)
            .build()

    private fun createPendingIntent(note: Note): PendingIntent {
        val bundle = Bundle().apply {
            putLong(DetailFragment.PARAM_ID, note.id)
        }
        return NavDeepLinkBuilder(mContext)
            .setGraph(R.navigation.graph_main)
            .setDestination(R.id.detailFragment)
            .setArguments(bundle)
            .createPendingIntent()
    }

    fun showNotification(note: Note) {
        notificationManager.notify(note.id.toInt(), createNotification(note))
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: NotifManager? = null
        private const val CHANNEL_ID = "geonote_id"
        private const val CHANNEL_NAME = "geonote_channel"
        private const val CHANNEL_DESCRIPTION = "geonote near position"


        fun getInstance(): NotifManager {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = NotifManager()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}