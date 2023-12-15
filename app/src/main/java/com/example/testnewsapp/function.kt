package com.example.testnewsapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat

//fun Context.isInternetAvailable(): Boolean {
//    val connectivityManager =
//        ContextCompat.getSystemService(this, ConnectivityManager::class.java)
//
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        val network = connectivityManager?.activeNetwork
//        val capabilities = connectivityManager?.getNetworkCapabilities(network)
//        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
//    } else {
//        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected
//    }
//}