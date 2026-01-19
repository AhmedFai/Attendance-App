package com.example.attendance.presentation.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppNavController(): NavHostController {
    return rememberNavController()
}