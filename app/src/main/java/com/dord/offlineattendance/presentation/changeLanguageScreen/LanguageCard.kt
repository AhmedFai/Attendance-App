package com.dord.offlineattendance.presentation.changeLanguageScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.dord.offlineattendance.ui.theme.dimens

@Composable
fun LanguageCard(
    item: LanguageItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val dimens = MaterialTheme.dimens
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(dimens.radiusL))
            .background(item.bgColor)
            .clickable { onClick() }
            .padding(dimens.spaceM)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dimens.iconXL)
                    .clip(CircleShape)
                    .background(item.circleColor)
            ) {
                Text(
                    text = item.short,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(dimens.spaceXS))

            Text(
                text = item.label,
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (isSelected) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier
                    .align(Alignment.TopEnd)
            )
        }
    }
}