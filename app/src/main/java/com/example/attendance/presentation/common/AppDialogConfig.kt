package com.example.attendance.presentation.common

data class AppDialogConfig(
    val title: String? = null,
    val message: String,
    val positiveText: String? = null,
    val negativeText: String? = null,
    val cancelable: Boolean = true,
    val onPositiveClick: (() -> Unit)? = null,
    val onNegativeClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)
