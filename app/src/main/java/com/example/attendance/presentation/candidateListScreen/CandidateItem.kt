package com.example.attendance.presentation.candidateListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import com.example.attendance.domain.model.CandidateListData
import com.example.attendance.domain.model.DomainType
import com.example.attendance.ui.theme.dimens

@Composable
fun CandidateItem(
    candidate: CandidateListData,
    domain: DomainType,
    onMarkAttendance: () -> Unit
) {

    val dimens = MaterialTheme.dimens

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.radiusM),
        elevation = CardDefaults.cardElevation(dimens.spaceXS),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(dimens.spaceM)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painter = painterResource(R.drawable.ic_profile),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimens.avatarS)
                        .clip(CircleShape)
                )

                Spacer(Modifier.width(dimens.spaceS))

                Column {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.name1)+ ": ",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                        Text(
                            text = candidate.name,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.roll_no)+ ": ",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                        Text(
                            text = candidate.rollNo.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray
                        )
                    }

//                    Text(
//                        text = stringResource(R.string.roll_no) + ": " + candidate.rollNo,
//                        style = MaterialTheme.typography.titleMedium,
//                        color = Color.Gray
//                    )
                }
            }

            Spacer(Modifier.height(dimens.spaceS))

//            Text(
//                text = stringResource(R.string.contact_number) + ": " + candidate.contactNumber,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Medium
//            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.contact_number) + ": ",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Text(
                    text = candidate.contactNumber,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
            }

            Divider(Modifier.padding(vertical = dimens.spaceXS))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.mark_today_attention),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "18 June 2025, Wednesday",
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onMarkAttendance() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = domain.primaryColor
                    ),
                    shape = RoundedCornerShape(dimens.radiusM)
                ) {
                    Text(
                        stringResource(R.string.mark_attendance),
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}