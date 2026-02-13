package com.example.attendance.presentation.home

data class HomeUiState(
    val isLoading: Boolean = true,
    val isSyncing: Boolean = false,

    val showLogoutDialog: Boolean = false,
    val showSyncDialog: Boolean = false,

    val userName: String = "",
    val email: String = "",
    val gender: String = "",
    val userId: String = "",
    val pendingCount: Int = 0,
    val syncedCount: Int = 0,
    val candidatesId : List<String> = emptyList()
)
