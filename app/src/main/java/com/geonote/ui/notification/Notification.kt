package com.geonote.ui.notification

import android.app.*
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.geonote.R
import com.geonote.data.model.db.Note

const val CHANNEL_ID = "1"

class Notification(val note: Note, val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    //val intent = Intent(context, MainActivity::class.java).putExtra("ID", note.id)

    private val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_adb_black_24dp)
        .setContentTitle(note.title)
        .setContentText(note.description)
        .setWhen(System.currentTimeMillis())
        .setShowWhen(true)
        .setStyle(setNotificationStyle(note))
        .setContentIntent(createPendingIntent(note.id))
        .setAutoCancel(true)
    private val notification = builder.build()

    fun showNotification() {
        notificationManager.notify(1, notification)
    }

    private fun createPendingIntent(id: Long): PendingIntent {
        val bundle = Bundle()
        bundle.putLong("ID", id)
        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.graph_main)
            .setDestination(R.id.detailFragment)
            .setArguments(bundle)
            .createPendingIntent()
    }


    private fun setNotificationStyle(note: Note): NotificationCompat.BigTextStyle {
        val bigText: NotificationCompat.BigTextStyle = NotificationCompat.BigTextStyle()
        bigText.setBigContentTitle(note.title)
        bigText.bigText(note.description)
        bigText.setSummaryText("Вы рядом!")
        return bigText
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel 1"
            val descriptionText = "notification channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}