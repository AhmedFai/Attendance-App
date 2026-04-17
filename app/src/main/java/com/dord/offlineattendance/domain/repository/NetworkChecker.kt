package com.dord.offlineattendance.domain.repository

interface NetworkChecker {
    fun isConnected(): Boolean
}