package com.iua.agustinpereyra.controller

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.iua.agustinpereyra.R

private const val NOTIFICATION_CHANNEL_ID = "DAILY_CHANNEL"
private const val NOTIFICATION_ID = 1

class NotificationHelper(private val context: Context?) {

    fun dailyNotification() {
        if (context != null) {
            val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.brand_cow_icon_foreground)
                .setContentTitle(context.getString(R.string.brand_name))
                .setContentText(context.getString(R.string.notification_msg))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Create channel if needed
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                // TODO: Optimize checking if channel doesn't exist yet
                val chanName = context.getString(R.string.channel_daily)
                val chanDescription = context.getString(R.string.channel_daily_description)
                val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, chanName, NotificationManager.IMPORTANCE_LOW)
                notificationChannel.description = chanDescription
                notificationManager.createNotificationChannel(notificationChannel)
            }

            notificationManager.notify(1, builder.build())
        }
    }
}