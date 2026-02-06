package com.example.attendance.presentation.attendance

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.attendance.R
import com.example.attendance.domain.model.DomainType
import com.example.attendance.presentation.attendance.bottomSheet.AttendanceSuccessBottomSheet
import com.example.attendance.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceSuccessSheetHost(
    domain: DomainType,
    show: Boolean,
    type: String,
    state: AttendanceUiState,
    onDismiss: () -> Unit
) {
    if (!show) return
    val dimens = MaterialTheme.dimens
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = dimens.bottomSheetTopShape, topEnd = dimens.bottomSheetTopShape),
        containerColor = Color.White
    ) {
        AttendanceSuccessBottomSheet(
            domain = domain,
            type = type,
            userName = state.candidate?.candidateName
                ?: state.faculty?.facultyName
                ?: "User",
            userImage = R.drawable.ic_profile,
            checkInTime = state.checkInTime ?: "--",
            checkOutTime = state.checkOutTime,
            totalHours = state.totalHours,
            onDone = onDismiss
        )
    }
}