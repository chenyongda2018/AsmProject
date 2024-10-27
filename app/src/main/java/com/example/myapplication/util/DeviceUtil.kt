package com.example.myapplication.util

import android.app.Service
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager

/**
 *
 * @author: cyd
 * @date: 2024/9/24
 * @since:
 */
object DeviceUtil {

    fun getDeviceId(context: Context): String {
        return try {
            val telephoneManager =
                context.getSystemService(Service.TELEPHONY_SERVICE) as? TelephonyManager
            telephoneManager?.deviceId ?: ""
        } catch (e: Throwable) {
            e.printStackTrace()
            ""
        }
    }

    fun getBrand(): String {
        return Build.BRAND
    }
}