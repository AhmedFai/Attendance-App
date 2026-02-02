package com.example.attendance.presentation.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.attendance.presentation.attendance.AttendanceScreen
import com.example.attendance.presentation.batchListScreen.BatchListScreen
import com.example.attendance.presentation.candidateListScreen.CandidateListScreen
import com.example.attendance.presentation.home.HomeScreen
import com.example.attendance.presentation.login.preLogin.LoginScreen

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
            val facultyId = "FACULTY_LOGIN_ID"
            BatchListScreen(
                onBack = { navController.popBackStack() },
                onCandidateList = { batchId ->
                    navController.navigate(Route.CandidateListScreen.withBatchId(batchId))
                },
                onMarkAttendance = {
                    navController.navigate(
                        Route.AttendanceScreen.withArgs(
                            userType = "FACULTY",
                            userId = facultyId
                        )
                    )
                }
            )
        }

        composable(route = "candidateListScreen/{batchId}") { backStackEntry ->
            val batchId =
                backStackEntry.arguments?.getString("batchId")?.toLong()
                    ?: return@composable
            CandidateListScreen(
                batchId,
                onBack = { navController.popBackStack() },
                onMarkAttendance = { candidateId ->
                    navController.navigate(
                        Route.AttendanceScreen.withArgs(
                            userType = "CANDIDATE",
                            userId = candidateId
                        )
                    )
                }
            )
        }

        composable(route = "attendanceScreen/{userType}/{userId}") { backStackEntry->
            val userType =
                backStackEntry.arguments?.getString("userType") ?: return@composable
            val userId =
                backStackEntry.arguments?.getString("userId") ?: return@composable
            AttendanceScreen(
                userType = userType,
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}