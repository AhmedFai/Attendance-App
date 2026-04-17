package com.dord.offlineattendance.presentation.security

import android.content.Context
import com.dord.offlineattendance.BuildConfig
import com.dord.offlineattendance.domain.security.SecurityChecker
import com.dord.offlineattendance.domain.security.SecurityResult

object SecurityManager {

    fun validate(context: Context): SecurityResult {

        // debug build me disable
        if (BuildConfig.DEBUG) {
            return SecurityResult.SAFE
        }

        return SecurityChecker.checkSecurity(context)
    }
}