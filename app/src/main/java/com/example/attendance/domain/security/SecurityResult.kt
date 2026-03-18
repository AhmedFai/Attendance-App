package com.example.attendance.domain.security

enum class SecurityResult {
    SAFE,
    ROOTED_DEVICE,
    EMULATOR_DEVICE,
    DEVELOPER_OPTIONS_ENABLED,
    FRIDA_DETECTED
}