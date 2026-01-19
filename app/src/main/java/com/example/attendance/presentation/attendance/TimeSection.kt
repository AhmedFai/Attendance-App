package com.example.attendance.presentation.attendance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendance.ui.theme.dimens
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TimeSection() {

    val timeFormatter = remember {
        SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
    }

    val dateFormatter = remember {
        SimpleDateFormat("dd MMMM yyyy, EEEE", Locale.getDefault())
    }

    val currentTime = remember { mutableStateOf("") }
    val currentDate = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val now = Date()
            currentTime.value = timeFormatter.format(now)
            currentDate.value = dateFormatter.format(now)
            delay(1000)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = currentTime.value,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(MaterialTheme.dimens.space2XS))

        Text(
            text = currentDate.value,
            color = Color.Gray
        )
    }
}