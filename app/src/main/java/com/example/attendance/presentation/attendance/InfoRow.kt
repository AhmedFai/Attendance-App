package com.example.attendance.presentation.attendance

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendance.ui.theme.dimens

@Composable
fun InfoRow(
    icon: ImageVector,
    text: String,
    showVerified: Boolean = false
) {

    val dimens = MaterialTheme.dimens

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = dimens.spaceXS)
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF000000),
            modifier = Modifier.size(dimens.iconS)
        )

        Spacer(Modifier.width(dimens.spaceXS))

        Text(
            text,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )

        if (showVerified) {
            Spacer(Modifier.width(dimens.spaceXS))
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(dimens.iconXS)
            )
        }
    }
}