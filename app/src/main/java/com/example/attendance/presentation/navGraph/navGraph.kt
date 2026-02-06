package com.example.attendance.presentation.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.attendance.presentation.attendance.AttendanceScreen
import com.example.attendance.presentation.batchListScreen.BatchListScreen
import com.example.attendance.presentation.candidateListScreen.CandidateListScreen
import com.example.attendance.presentation.home.HomeScreen
import com.example.attendance.presentation.login.preLogin.LoginScreen
import com.example.attendance.presentation.login.preLogin.LoginViewModel

@Composable
fun navGraph(
    startDestination: String
) {
    val navController = rememberAppNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
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
            val facultyId = loginViewModel.session?.userId
            BatchListScreen(
                onBack = { navController.popBackStack() },
                onCandidateList = { batchId ->
                    navController.navigate(Route.CandidateListScreen.withBatchId(batchId))
                },
                onMarkAttendance = { batchId->
                    navController.navigate(
                        Route.AttendanceScreen.withArgs(
                            userType = "FACULTY",
                            userId = facultyId!!,
                            batchId = batchId
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
                            userId = candidateId,
                            batchId = batchId
                        )
                    )
                }
            )
        }

        composable(route = "attendanceScreen/{userType}/{userId}/{batchId}") { backStackEntry->
            val userType =
                backStackEntry.arguments?.getString("userType") ?: return@composable
            val userId =
                backStackEntry.arguments?.getString("userId") ?: return@composable
            val batchId =
                backStackEntry.arguments?.getString("batchId")?.toLong() ?: return@composable
            AttendanceScreen(
                userType = userType,
                userId = userId,
                batchId = batchId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}