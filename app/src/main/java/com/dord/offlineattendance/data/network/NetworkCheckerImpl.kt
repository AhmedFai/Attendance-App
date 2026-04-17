package com.dord.offlineattendance.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.dord.offlineattendance.domain.repository.NetworkChecker

class NetworkCheckerImpl(
    private val context: Context
) : NetworkChecker {
    override fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val network = cm.activeNetwork ?: return false
        val cap = cm.getNetworkCapabilities(network) ?: return false

        return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}