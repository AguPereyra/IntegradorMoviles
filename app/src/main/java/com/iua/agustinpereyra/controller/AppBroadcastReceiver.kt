package com.iua.agustinpereyra.controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.VibrationEffect
import android.os.Vibrator

class AppBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Vibrate and send notification
        val notificationHelper = NotificationHelper(context)
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        notificationHelper.dailyNotification()
        // TODO: Check to change for not deprecated
        vibrator.vibrate(2000)

    }

}