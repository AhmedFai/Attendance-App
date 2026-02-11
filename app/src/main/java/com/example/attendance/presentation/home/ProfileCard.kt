package com.example.attendance.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.attendance.domain.model.DomainType
import com.example.attendance.ui.theme.dimens

@Composable
fun ProfileCard(
    domain: DomainType,
    userName: String,
    email: String,
    gender: String,
    onLogout: () -> Unit
) {

    val dimens = MaterialTheme.dimens

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.radiusXL),
        elevation = CardDefaults.cardElevation(dimens.spaceXS),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(dimens.spaceM),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = if (gender == "M"){
                    painterResource(R.drawable.ic_profile_male)
                }else{
                    painterResource(R.drawable.ic_profile_female)
                },
                contentDescription = null,
                modifier = Modifier
                    .size(dimens.avatarM)
                    .clip(CircleShape)
                    .background(domain.primaryColor.copy(alpha = 0.18f))
            )

            Spacer(Modifier.width(dimens.spaceM))

            Column(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .weight(1f)
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(
                    containerColor = domain.primaryColor
                ),
                shape = RoundedCornerShape(dimens.radiusM)
            ) {
                Text(
                    stringResource(R.string.logout),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}