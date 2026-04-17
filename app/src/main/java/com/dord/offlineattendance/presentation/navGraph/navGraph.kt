package com.dord.offlineattendance.presentation.navGraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dord.offlineattendance.presentation.attendance.AttendanceScreen
import com.dord.offlineattendance.presentation.batchListScreen.BatchListScreen
import com.dord.offlineattendance.presentation.candidateListScreen.CandidateListScreen
import com.dord.offlineattendance.presentation.changeLanguageScreen.LanguageScreen
import com.dord.offlineattendance.presentation.home.HomeScreen
import com.dord.offlineattendance.presentation.login.preLogin.LoginScreen
import com.dord.offlineattendance.presentation.login.preLogin.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
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
            LoginScreen(
                onChangelanguage = {
                    navController.navigate(Route.LanguageScreen.routeName)
                },
            )
        }

        composable(Route.LanguageScreen.routeName) {
            LanguageScreen(
                onBack = { navController.popBackStack() },
                onLanguageSelected = {
                    navController.popBackStack() // after change
                }
            )
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
                onMarkAttendance = { batchId ->
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

        composable(route = "attendanceScreen/{userType}/{userId}/{batchId}") { backStackEntry ->
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