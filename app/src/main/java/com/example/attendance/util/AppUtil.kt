package com.example.attendance.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.security.MessageDigest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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

    fun calculateBatchProgress(
        startDate: String,
        endDate: String
    ): Float {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val start = LocalDate.parse(startDate, formatter)
        val end = LocalDate.parse(endDate, formatter)
        val today = LocalDate.now()

        val totalDays = ChronoUnit.DAYS.between(start, end).coerceAtLeast(1)
        val passedDays = ChronoUnit.DAYS.between(start, today).coerceAtLeast(0)

        return (passedDays.toFloat() / totalDays.toFloat())
            .coerceIn(0f, 1f)
    }

}