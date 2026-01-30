package com.example.attendance.domain.repository

interface NetworkChecker {
    fun isConnected(): Boolean
}