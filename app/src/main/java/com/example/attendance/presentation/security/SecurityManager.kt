package com.example.attendance.presentation.security

import android.content.Context
import com.example.attendance.BuildConfig
import com.example.attendance.domain.security.SecurityChecker
import com.example.attendance.domain.security.SecurityResult

object SecurityManager {

    fun validate(context: Context): SecurityResult {

        // debug build me disable
        if (BuildConfig.DEBUG) {
            return SecurityResult.SAFE
        }

        return SecurityChecker.checkSecurity(context)
    }
}