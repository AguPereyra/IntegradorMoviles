package com.iua.agustinpereyra.controller

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat.getSystemService

class NetworkHelper {

    companion object {
        fun isNetworkConnected(context: Context?) : Boolean {
            // Get connectivity API service
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // Get Network Info
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            val conStatus = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            return if (conStatus == null) {
                false
            } else {
                conStatus
            }
        }
    }
}