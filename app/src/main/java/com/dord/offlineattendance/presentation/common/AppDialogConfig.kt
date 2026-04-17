package com.dord.offlineattendance.presentation.common

import com.dord.offlineattendance.domain.model.DomainType

data class AppDialogConfig(
    val domainType: DomainType,
    val title: String? = null,
    val message: String,
    val positiveText: String? = null,
    val negativeText: String? = null,
    val cancelable: Boolean = true,
    val onPositiveClick: (() -> Unit)? = null,
    val onNegativeClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)
