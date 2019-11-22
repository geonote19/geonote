package com.geonote.ui.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.geonote.R
import com.geonote.data.model.db.Note
import com.geonote.ui.MainActivity

const val CHANNEL_ID = "1"

class Notification(val note: Note, val context: Context) {

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val intent = Intent(context, MainActivity::class.java).putExtra("ID", note.id)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
    val title = note.title
    val text = note.description

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_adb_black_24dp)
        .setContentTitle(title)
        .setContentText(text)
        .setWhen(System.currentTimeMillis()).setShowWhen(true).setStyle(setNotificationStyle(note))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
    val notification = builder.build()

    fun showNotification() {
        notificationManager.notify(1, notification)
    }

    fun setNotificationStyle(note: Note): NotificationCompat.BigTextStyle {
        val bigText: NotificationCompat.BigTextStyle = NotificationCompat.BigTextStyle()
        bigText.setBigContentTitle(note.title)
        bigText.bigText(note.description)
        bigText.setSummaryText("Какой-то текст")
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