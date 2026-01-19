package com.example.attendance.presentation.attendance

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendance.R
import com.example.attendance.domain.model.AttendanceType
import com.example.attendance.domain.model.attendanceModel.CandidateUiModel
import com.example.attendance.domain.model.attendanceModel.SelfUserUiModel
import com.example.attendance.ui.theme.dimens

@Composable
fun ProfileSection(
    type: AttendanceType,
    candidate: CandidateUiModel?,
    selfUser: SelfUserUiModel?
) {

    val dimens = MaterialTheme.dimens

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(R.drawable.ic_profile),
            contentDescription = null,
            modifier = Modifier
                .size(dimens.avatarM)
                .clip(CircleShape)
        )

        Spacer(Modifier.width(dimens.spaceM))

        Column {

            Text(
                text =
                    if (type == AttendanceType.CANDIDATE)
                        candidate!!.name
                    else
                        selfUser!!.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(dimens.space2XS))

            Text(
                text =
                    if (type == AttendanceType.CANDIDATE)
                        stringResource(R.string.roll_no) + " " + candidate!!.rollNo
                    else
                        stringResource(R.string.loginId) + ": " + selfUser!!.loginId,
                color = Color.Gray,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }

    Spacer(Modifier.height(dimens.spaceS))

    InfoRow(Icons.Default.Call, candidate?.contact ?: selfUser!!.contact)
    InfoRow(Icons.Default.Email, candidate?.email ?: selfUser!!.email)
    InfoRow(Icons.Default.People, candidate?.gender ?: selfUser!!.gender)
    InfoRow(Icons.Default.DateRange, candidate?.dob ?: selfUser!!.dob)
}