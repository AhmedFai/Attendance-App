package com.example.attendance.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.attendance.R
import com.example.attendance.domain.model.DomainType
import com.example.attendance.ui.theme.dimens

@Composable
fun ActionGrid(
    domain: DomainType,
    pendingCount: Int,
    syncedCount: Int,
    onMarkAttendance: () -> Unit,
    onOfflineData: () -> Unit,
    onFetchEmbeddings: () -> Unit,
    onSync: () -> Unit,
    onTotalSync: () -> Unit
) {

    val dimens = MaterialTheme.dimens

    Column(
        verticalArrangement = Arrangement.spacedBy(dimens.spaceM)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimens.spaceM)
        ) {
            ActionCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.mark_attendance),
                icon = R.drawable.ic_attendance,
                color = domain.secondaryColor,
                onClick = onMarkAttendance
            )
            ActionCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.offline_data) + " ($pendingCount)",
                icon = R.drawable.ic_database,
                color = domain.secondaryColor,
                onClick = onOfflineData
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.get_register_embeddings),
                icon = R.drawable.ic_face,
                color = domain.secondaryColor,
                onClick = onFetchEmbeddings
            )
            ActionCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.sync_to_server),
                icon = R.drawable.ic_sync,
                color = domain.secondaryColor,
                onClick = onSync
            )
        }

        ActionCardTotalSync(
            title = stringResource(R.string.sync_data) + " ($syncedCount)",
            icon = R.drawable.ic_total_sync,
            color = domain.primaryColor,
            onClick = onTotalSync
        )

    }
}