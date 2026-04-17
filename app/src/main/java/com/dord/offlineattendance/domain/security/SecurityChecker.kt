package com.dord.offlineattendance.domain.security

import android.content.Context
import android.os.Build
import android.provider.Settings
import java.io.File

object SecurityChecker {

    fun checkSecurity(context: Context): SecurityResult {

        if (isDeveloperOptionsEnabled(context)) {
            return SecurityResult.DEVELOPER_OPTIONS_ENABLED
        }

        if (isEmulator()) {
            return SecurityResult.EMULATOR_DEVICE
        }

        if (isRooted()) {
            return SecurityResult.ROOTED_DEVICE
        }

        if (isFridaRunning()) {
            return SecurityResult.FRIDA_DETECTED
        }

        return SecurityResult.SAFE
    }

    private fun isDeveloperOptionsEnabled(context: Context): Boolean {

        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
            0
        ) != 0
    }

    private fun isEmulator(): Boolean {

        return (
                Build.FINGERPRINT.startsWith("generic")
                        || Build.FINGERPRINT.contains("vbox")
                        || Build.FINGERPRINT.contains("test-keys")
                        || Build.MODEL.contains("Emulator")
                        || Build.MODEL.contains("Android SDK built for x86")
                        || Build.MANUFACTURER.contains("Genymotion")
                        || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                        || "google_sdk" == Build.PRODUCT
                )
    }

    private fun isRooted(): Boolean {

        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )

        return paths.any { File(it).exists() }
    }

    fun isFridaRunning(): Boolean {
        val suspiciousProcesses = listOf(
            "frida-server",
            "frida-agent",
            "frida-helper"
        )

        return try {
            val process = Runtime.getRuntime().exec("ps")
            val reader = process.inputStream.bufferedReader()

            reader.readLines().any { line ->
                suspiciousProcesses.any { line.contains(it, ignoreCase = true) }
            }
        } catch (e: Exception) {
            false
        }
    }
}