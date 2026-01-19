package com.example.attendance.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

@Composable
fun AppUtils(
    app_Dimens : Dimens,
    content: @Composable () -> Unit
){
    val appDimens = remember {
        app_Dimens
    }

    CompositionLocalProvider(LocalAppDimens provides appDimens) {
        content()
    }
}

val LocalAppDimens = compositionLocalOf {
    CompactDimens
}
