package com.example.attendance.presentation.candidateListScreen.shimmer

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.attendance.presentation.home.shimmer.shimmerBrush
import com.example.attendance.ui.theme.dimens

@Composable
fun CandidateListShimmer() {

    val dimens = MaterialTheme.dimens

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimens.spaceM),
        verticalArrangement = Arrangement.spacedBy(dimens.spaceS)
    ) {
        items(4) {
            CandidateItemShimmer()
        }
    }
}

@Composable
fun CandidateItemShimmer() {

    val dimens = MaterialTheme.dimens

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.radiusM),
        elevation = CardDefaults.cardElevation(dimens.spaceXS),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(dimens.spaceM)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                ShimmerBox(
                    modifier = Modifier
                        .size(dimens.avatarS),
                    shape = CircleShape
                )

                Spacer(Modifier.width(dimens.spaceS))

                Column {

                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(14.dp)
                    )

                    Spacer(Modifier.height(6.dp))

                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(14.dp)
                    )
                }
            }

            Spacer(Modifier.height(dimens.spaceS))

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(14.dp)
            )

            Spacer(Modifier.height(dimens.spaceXS))

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Spacer(Modifier.height(dimens.spaceXS))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(14.dp)
                    )

                    Spacer(Modifier.height(6.dp))

                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(12.dp)
                    )
                }

                Spacer(Modifier.width(dimens.spaceS))

                ShimmerBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    shape = RoundedCornerShape(dimens.radiusM)
                )
            }
        }
    }
}

@Composable
fun ShimmerBox(
    modifier: Modifier,
    shape: Shape = RoundedCornerShape(6.dp)
) {
    Box(
        modifier = modifier
            .background(
                brush = shimmerBrush(),
                shape = shape
            )
    )
}