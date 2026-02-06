package com.example.attendance.presentation.attendance.bottomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.example.attendance.R
import com.example.attendance.domain.model.DomainType

@Composable
fun AttendanceSuccessBottomSheet(
    domain: DomainType,
    type: String, // "CHECK-IN" or "CHECK-OUT"
    userName: String,
    userImage: Int, // drawable resource
    checkInTime: String,
    checkOutTime: String?,
    totalHours: String?,
    onDone: () -> Unit
) {
    val isCheckIn = type == "CHECK-IN"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 👤 User Image
        Image(
            painter = painterResource(userImage),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.height(12.dp))

        // 👤 Name
        Text(
            text = userName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(16.dp))

        // ✅ Success Icon
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = domain.primaryColor,
            modifier = Modifier.size(72.dp)
        )

        Spacer(Modifier.height(12.dp))

        // ✅ Title
        Text(
            text = if (isCheckIn) stringResource(R.string.checkInSuccess) else stringResource(R.string.checkOutSuccess),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        // 🕒 Check-In Time
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.check_in), color = Color.Gray)
            Text(checkInTime, fontWeight = FontWeight.Medium)
        }

        // 🕒 Check-Out + Total (only for checkout)
        if (!isCheckIn) {

            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.check_out), color = Color.Gray)
                Text(checkOutTime ?: "--", fontWeight = FontWeight.Medium)
            }

            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.total_hours), color = Color.Gray)
                Text(totalHours ?: "--", fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.height(24.dp))

        // 🔘 Button
        Button(
            onClick = onDone,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(domain.primaryColor)
        ) {
            Text(if (isCheckIn) stringResource(R.string.ok) else stringResource(R.string.done))
        }
    }
}
