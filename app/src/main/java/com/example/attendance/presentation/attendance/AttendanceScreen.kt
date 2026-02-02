package com.example.attendance.presentation.attendance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.attendance.R
import com.example.attendance.presentation.common.Toolbar
import com.example.attendance.ui.theme.dimens
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AttendanceScreen(
    userType: String,
    userId: String,
    onBack: () -> Unit
) {
    val viewModel: AttendanceViewModel = hiltViewModel()
    val dimens = MaterialTheme.dimens

    val systemUiController = rememberSystemUiController()
    val state = viewModel.uiState

    DisposableEffect(viewModel.domain) {
        systemUiController.setStatusBarColor(
            color = viewModel.domain.primaryColor,
            darkIcons = false
        )

        onDispose {
            // kuch nahi – next screen handle karega
        }
    }

    LaunchedEffect(userType, userId) {
        viewModel.loadUser(userType, userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            )
    ) {

        // 🔹 TOOLBAR
        Toolbar(
            title =
                if (userType == "CANDIDATE")
                    stringResource(R.string.mark_candidate_attendance)
                else
                    stringResource(R.string.mark_self_attendance),
            domain = viewModel.domain,
            onBack = onBack
        )

        when{
            state.isLoading -> {
                //AttendanceShimmer()
            }
            state.error != null -> {

            }
            else -> {
                Spacer(Modifier.height(dimens.spaceM))

                // 🔹 CARD
                Card(
                    modifier = Modifier
                        .padding(horizontal = dimens.spaceM)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(dimens.radiusM),
                    elevation = CardDefaults.cardElevation(dimens.spaceXS),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(modifier = Modifier.padding(dimens.spaceM)) {

                        // Profile
                        ProfileSection(
                            type = userType,
                            candidate = viewModel.uiState.candidate,
                            faculty = viewModel.uiState.faculty
                        )

                        Divider(Modifier.padding(vertical = dimens.spaceM))

                        // Time
                        TimeSection()

                        Spacer(Modifier.height(dimens.spaceM))

                        // Buttons
                        AttendanceButtons(
                            domain = viewModel.domain,
                            status = viewModel.attendanceStatus,
                            onCheckIn = { viewModel.onCheckIn() },
                            onCheckOut = { viewModel.onCheckOut() }
                        )

                        Divider(Modifier.padding(vertical = dimens.spaceM))

                        // States
                        AttendanceStats()
                    }
                }
            }
        }
    }
}