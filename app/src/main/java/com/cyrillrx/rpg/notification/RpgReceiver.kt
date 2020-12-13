package com.cyrillrx.rpg.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.cyrillrx.rpg.LoadingActivity
import com.cyrillrx.rpg.R


class RpgReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getIntExtra(NotificationsMessage.TYPE_EXTRA, 0)
        val intentToRepeat = Intent(context, LoadingActivity::class.java)
        intentToRepeat.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(context, type, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT)
        val nm = NotificationsMessage().getNotificationManager(context)
        val notification: Notification = buildNotification(context, pendingIntent, nm as NotificationManager?).build()
        nm?.notify(type, notification)
    }

    fun buildNotification(context: Context, pendingIntent: PendingIntent?, nm: NotificationManager?): NotificationCompat.Builder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default",
                "Daily Notification",
                NotificationManager.IMPORTANCE_HIGH)
            channel.description = "Daily Notification"
            nm?.createNotificationChannel(channel)
        }
        Log.e("Push", "Push")
        return NotificationCompat.Builder(context, "default")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Эй, нy ты гдe? Тyт тaкoеее..!")
            .setAutoCancel(true)
    }
}