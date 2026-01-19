package com.example.attendance.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.security.MessageDigest

object AppUtil {

    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context) : String{

        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun sha512(input: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-512")
            .digest(input.toByteArray())

        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

}