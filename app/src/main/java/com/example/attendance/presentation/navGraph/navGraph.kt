package com.example.attendance.presentation.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.attendance.presentation.attendance.AttendanceScreen
import com.example.attendance.presentation.batchListScreen.BatchListScreen
import com.example.attendance.presentation.candidateListScreen.CandidateListScreen
import com.example.attendance.presentation.home.HomeScreen
import com.example.attendance.presentation.login.LoginScreen

@Composable
fun navGraph(
    startDestination: String
) {
    val navController = rememberAppNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.LoginScreen.routeName) {
            LoginScreen()
        }

        composable(Route.HomeScreen.routeName) {
            HomeScreen(
                onLogout = {
                    navController.navigate(Route.LoginScreen.routeName) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onMarkAttendance = {
                    navController.navigate(Route.BatchListScreen.routeName)
                }
            )
        }

        composable(Route.BatchListScreen.routeName) {
            BatchListScreen(
                onBack = { navController.popBackStack() },
                onCandidateList = {
                    navController.navigate(Route.CandidateListScreen.routeName)
                },
                onMarkAttendance = {
                    navController.navigate(Route.AttendanceScreen.routeName)
                }
            )
        }

        composable(Route.CandidateListScreen.routeName) {
            CandidateListScreen(
                onBack = { navController.popBackStack() },
                onMarkAttendance = {
                    navController.navigate(Route.AttendanceScreen.routeName)
                }
            )
        }

        composable(Route.AttendanceScreen.routeName) {
            AttendanceScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}