package com.example.attendance.presentation.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.attendance.domain.model.DomainType
import com.example.attendance.ui.theme.AttendanceTheme

@Composable
fun AppAlertDialog(
    config: AppDialogConfig
) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = {
            if (config.cancelable) {
                config.onDismiss?.invoke()
            }
        },
        title = {
            config.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Text(
                text = config.message,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            config.positiveText?.let {
                TextButton(
                    onClick = {
                        config.onPositiveClick?.invoke()
                        config.onDismiss?.invoke()
                    }
                ) {
                    Text(it, color = Color.Black)
                }
            }
        },
        dismissButton = {
            config.negativeText?.let {
                TextButton(
                    onClick = {
                        config.onNegativeClick?.invoke()
                        config.onDismiss?.invoke()
                    }
                ) {
                    Text(it, color = Color.Black)
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun AlertDialogPreview(){

            AppAlertDialog(
                config = AppDialogConfig(
                    domainType = DomainType.DDUGKY,
                    title = "Alert",
                    message = "Alert dialog preview",
                    positiveText = "OK",
                    negativeText = "Cancel",
                    onPositiveClick = {},
                    onNegativeClick = {},
                    onDismiss = {}
                )
            )

}
