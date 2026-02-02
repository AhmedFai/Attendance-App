package com.example.attendance.presentation.batchListScreen

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendance.R
import com.example.attendance.data.local.entity.BatchEntity
import com.example.attendance.ui.theme.dimens
import com.example.attendance.util.AppUtil.calculateBatchProgress

@Composable
fun BatchItem(
    batch: BatchEntity,
    onClick: () -> Unit
) {

    val dimens = MaterialTheme.dimens

    val progress = calculateBatchProgress(
        startDate = batch.startDate,
        endDate = batch.endDate
    )
    val (statusText, statusColor) = getBatchStatus(progress)

    Card(
        shape = RoundedCornerShape(dimens.radiusM),
        elevation = CardDefaults.cardElevation(dimens.spaceXS),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.padding(dimens.spaceM)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.batch_id) + ": " + batch.batchId,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = statusText,
                    color = statusColor,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(Modifier.height(dimens.spaceXS))
            Text(
                text = batch.batchName,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Spacer(Modifier.height(dimens.spaceXS))
            Text(
                text = "${batch.startDate}  →  ${batch.endDate}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Composable
private fun getBatchStatus(progress: Float): Pair<String, Color> {
    return when {
        progress < 0.2f ->
            stringResource(R.string._new) to Color(0xFF2E7D32)

        progress < 0.8f ->
            stringResource(R.string.active) to Color(0xFFF9A825)

        else ->
            stringResource(R.string.endingSoon) to Color(0xFFC62828)
    }
}