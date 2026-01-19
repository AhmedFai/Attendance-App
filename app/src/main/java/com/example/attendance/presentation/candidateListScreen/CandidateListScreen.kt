package com.example.attendance.presentation.candidateListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.attendance.R
import com.example.attendance.presentation.common.Toolbar
import com.example.attendance.ui.theme.dimens
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun CandidateListScreen(
    viewModel: CandidateListViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onMarkAttendance: () -> Unit
) {


    val domain = viewModel.domain
    val systemUiController = rememberSystemUiController()

    val dimens = MaterialTheme.dimens

    DisposableEffect(viewModel.domain) {
        systemUiController.setStatusBarColor(
            color = viewModel.domain.primaryColor,
            darkIcons = false
        )

        onDispose {
            // kuch nahi – next screen handle karega
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F5F8))
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            )
    ) {

        Toolbar(
            title = stringResource(R.string.candidate_attendance),
            domain = domain,
            onBack = onBack
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(dimens.spaceM),
            verticalArrangement = Arrangement.spacedBy(dimens.spaceS)
        ) {
            items(viewModel.candidates) { candidate ->
                CandidateItem(
                    candidate,
                    domain,
                    onMarkAttendance = {
                        onMarkAttendance()
                    }
                )
            }
        }
    }
}