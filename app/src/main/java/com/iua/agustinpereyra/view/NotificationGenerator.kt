package com.iua.agustinpereyra.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iua.agustinpereyra.R

class NotificationGenerator {
    companion object {
        fun noInternetConnectionAlert(context: Context?) {
            if (context != null) {
                val alertBuilder = AlertDialog.Builder(context)

                // Set dialog data
                alertBuilder.setMessage(R.string.no_internet_msg)
                    .setPositiveButton(
                        R.string.no_internet_positive_btn,
                        DialogInterface.OnClickListener { dialog, which ->
                            // Do nothin, just informing the user
                        })
                    .setTitle(R.string.no_internet_title)
                    .create()

                // Show
                alertBuilder.show()
            }
        }
    }
}