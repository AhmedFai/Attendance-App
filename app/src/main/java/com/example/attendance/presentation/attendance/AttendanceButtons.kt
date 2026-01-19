package com.example.attendance.presentation.attendance

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.attendance.R
import com.example.attendance.domain.model.AttendanceStatus
import com.example.attendance.domain.model.DomainType
import com.example.attendance.ui.theme.dimens

@Composable
fun AttendanceButtons(
    domain: DomainType,
    status: AttendanceStatus,
    onCheckIn: () -> Unit,
    onCheckOut: () -> Unit
) {

    val dimens = MaterialTheme.dimens

    val isCheckedIn = status == AttendanceStatus.CHECKED_IN
    val isCheckedOut = status == AttendanceStatus.CHECKED_OUT

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.spaceS)
    ) {

        if (isCheckedIn || status == AttendanceStatus.NONE) {
            Button(
                onClick = onCheckIn,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(domain.primaryColor),
                shape = RoundedCornerShape(dimens.radiusM)
            ) {
                Text(
                    stringResource(R.string.check_in),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            OutlinedButton(
                onClick = onCheckIn,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(dimens.radiusM),
                border = BorderStroke(1.dp, domain.primaryColor)
            ) {
                Text(
                    stringResource(R.string.check_in),
                    color = domain.primaryColor,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        if (isCheckedOut) {
            Button(
                onClick = onCheckOut,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(domain.primaryColor),
                shape = RoundedCornerShape(dimens.radiusM)
            ) {
                Text(
                    stringResource(R.string.check_out),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            OutlinedButton(
                onClick = onCheckOut,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(dimens.radiusM),
                border = BorderStroke(1.dp, domain.primaryColor)
            ) {
                Text(
                    stringResource(R.string.check_out),
                    color = domain.primaryColor,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}