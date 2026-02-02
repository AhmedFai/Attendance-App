package com.example.attendance.presentation.attendance

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.attendance.R
import com.example.attendance.data.local.entity.CandidateEntity
import com.example.attendance.data.local.entity.FacultyEntity
import com.example.attendance.ui.theme.dimens

@Composable
fun ProfileSection(
    type: String,
    candidate: CandidateEntity?,
    faculty: FacultyEntity?
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
                    if (type == "CANDIDATE")
                        candidate!!.candidateName
                    else
                        faculty!!.facultyName,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(dimens.space2XS))

            Text(
                text =
                    if (type == "CANDIDATE")
                        stringResource(R.string.roll_no) + ": " + candidate!!.rollNo
                    else
                        stringResource(R.string.loginId) + ": " + faculty!!.loginId,
                color = Color.Gray,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }

    Spacer(Modifier.height(dimens.spaceS))

    val uiUser = when {
        candidate != null -> AttendanceUserUi(
            mobile = candidate.mobileNo ?: "-",
            email = candidate.candidateEmail ?: "-",
            gender = candidate.gender ?: "-",
            dob = candidate.dateOfBirth ?: "-"
        )

        faculty != null -> AttendanceUserUi(
            mobile = faculty.mobileNo ?: "-",
            email = faculty.emailId ?: "-",
            gender = faculty.gender ?: "-",
            dob = faculty.dob ?: "-"
        )

        else -> null
    }

    InfoRow(Icons.Default.Call, uiUser?.mobile.toString())
    InfoRow(Icons.Default.Email, uiUser?.email.toString())
    InfoRow(Icons.Default.People, uiUser?.gender.toString())
    InfoRow(Icons.Default.DateRange, uiUser?.dob.toString())
}