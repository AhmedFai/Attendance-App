package com.example.attendance.presentation.home.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun ActionCardShimmer(
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    val brush = shimmerBrush()

    Card(
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(dimens.radiusL),
        elevation = CardDefaults.cardElevation(dimens.spaceXS),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimens.spaceM),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(dimens.iconXL)
                    .clip(CircleShape)
                    .background(brush)
            )

            Spacer(Modifier.height(dimens.spaceS))

            Box(
                modifier = Modifier
                    .height(14.dp)
                    .fillMaxWidth(0.7f)
                    .clip(RoundedCornerShape(dimens.radiusS))
                    .background(brush)
            )
        }
    }
}