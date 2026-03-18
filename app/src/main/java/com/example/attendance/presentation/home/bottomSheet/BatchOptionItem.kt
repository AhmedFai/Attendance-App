package com.example.attendance.presentation.home.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.attendance.R
import com.example.attendance.data.local.entity.BatchEntity
import com.example.attendance.ui.theme.dimens

@Composable
fun BatchOptionItem(
    batch: BatchEntity,
    onClick: (String) -> Unit
) {

    val dimens = MaterialTheme.dimens

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimens.radiusM),
        elevation = CardDefaults.cardElevation(dimens.spaceXS),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = { onClick(batch.batchId.toString()) }
    ) {

        Column(
            modifier = Modifier.padding(dimens.spaceM)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.batch_id) + ": " + batch.batchRegNo,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
            }
            Spacer(Modifier.height(dimens.spaceXS))
            Text(
                text = batch.batchName,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        }

    }

}