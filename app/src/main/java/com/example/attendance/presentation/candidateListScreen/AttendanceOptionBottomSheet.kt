package com.example.attendance.presentation.candidateListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendance.R
import com.example.attendance.domain.model.DomainType
import com.example.attendance.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceOptionBottomSheet(
    domain: DomainType,
    onDismiss: () -> Unit,
    onCandidateAttendance: () -> Unit,
    onSelfAttendance: () -> Unit
) {

    val dimens = MaterialTheme.dimens

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = dimens.bottomSheetTopShape, topEnd = dimens.bottomSheetTopShape),
        containerColor = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.spaceM),
            verticalArrangement = Arrangement.spacedBy(dimens.spaceM)
        ) {

            Text(
                text = stringResource(R.string.select_an_option),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            AttendanceOptionItem(
                title = stringResource(R.string.mark_candidate_attendance),
                icon = R.drawable.candidate,
                color = domain.primaryColor,
                onClick = onCandidateAttendance
            )

            AttendanceOptionItem(
                title = stringResource(R.string.mark_self_attendance),
                icon = R.drawable.faculty,
                color = domain.primaryColor,
                onClick = onSelfAttendance
            )

            Spacer(Modifier.height(dimens.spaceXS))

            TextButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.cancel), color = Color.Gray)
            }
        }
    }
}