package com.example.alcancia.extras

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService

class Network {
    companion object{
        fun netCheck(activity: AppCompatActivity): Boolean{
            val connectivityManager = activity.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected

        }
    }

}