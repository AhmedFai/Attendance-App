package com.example.attendance.presentation.batchListScreen.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.attendance.presentation.home.shimmer.shimmerBrush
import com.example.attendance.ui.theme.dimens

@Composable
fun BatchListShimmer() {

    val dimens = MaterialTheme.dimens

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimens.spaceM),
        verticalArrangement = Arrangement.spacedBy(dimens.spaceS)
    ) {
        items(3) {
            BatchItemShimmer()
        }
    }
}

@Composable
fun BatchItemShimmer() {

    val dimens = MaterialTheme.dimens

    Card(
        shape = RoundedCornerShape(dimens.radiusM),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(dimens.spaceXS)
    ) {
        Column(
            modifier = Modifier.padding(dimens.spaceM)
        ) {

            // 🔹 Row: Batch ID + Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                ShimmerLine(
                    modifier = Modifier
                        .fillMaxWidth(0.35f)
                        .height(16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                ShimmerLine(
                    modifier = Modifier
                        .width(70.dp)
                        .height(16.dp)
                )
            }

            Spacer(Modifier.height(dimens.spaceXS))

            // 🔹 Batch Name
            ShimmerLine(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(20.dp)
            )

            Spacer(Modifier.height(dimens.spaceXS))

            // 🔹 Start → End Date
            ShimmerLine(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(16.dp)
            )
        }
    }
}

@Composable
fun ShimmerLine(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .background(
                brush = shimmerBrush(),
                shape = RoundedCornerShape(6.dp)
            )
    )
}