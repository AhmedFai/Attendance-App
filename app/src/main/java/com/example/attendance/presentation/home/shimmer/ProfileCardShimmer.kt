package com.example.attendance.presentation.home.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.attendance.ui.theme.dimens

@Composable
fun ProfileCardShimmer() {
    val dimens = MaterialTheme.dimens
    val brush = shimmerBrush()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.radiusXL),
        elevation = CardDefaults.cardElevation(dimens.spaceXS),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Row(
            modifier = Modifier.padding(dimens.spaceM),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Avatar
            Box(
                modifier = Modifier
                    .size(dimens.avatarM)
                    .clip(CircleShape)
                    .background(brush)
            )

            Spacer(Modifier.width(dimens.spaceM))

            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .height(18.dp)
                        .fillMaxWidth(0.7f)
                        .clip(RoundedCornerShape(dimens.radiusS))
                        .background(brush)
                )

                Spacer(Modifier.height(dimens.spaceXS))

                Box(
                    modifier = Modifier
                        .height(14.dp)
                        .fillMaxWidth(0.5f)
                        .clip(RoundedCornerShape(dimens.radiusS))
                        .background(brush)
                )
            }

            Spacer(Modifier.width(dimens.spaceM))

            // Logout button placeholder
            Box(
                modifier = Modifier
                    .height(dimens.buttonHeight)
                    .width(100.dp)
                    .clip(RoundedCornerShape(dimens.radiusM))
                    .background(brush)
            )
        }
    }
}