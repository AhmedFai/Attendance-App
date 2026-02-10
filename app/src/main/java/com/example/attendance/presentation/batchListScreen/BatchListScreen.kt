package com.example.attendance.presentation.batchListScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.attendance.R
import com.example.attendance.presentation.batchListScreen.shimmer.BatchListShimmer
import com.example.attendance.presentation.candidateListScreen.AttendanceOptionBottomSheet
import com.example.attendance.presentation.common.Toolbar
import com.example.attendance.ui.theme.dimens
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BatchListScreen(
    viewModel: BatchListViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onCandidateList: (Long) -> Unit,
    onMarkAttendance: (Long) -> Unit
) {
    var showAttendanceSheet by remember { mutableStateOf(false) }
    //val systemUiController = rememberSystemUiController()
    val dimens = MaterialTheme.dimens
    var selectedScreen: String = ""
    var selectedBatchId by remember { mutableStateOf<Long?>(null) }

//    DisposableEffect(viewModel.domain) {
//        systemUiController.setStatusBarColor(
//            color = viewModel.domain.primaryColor,
//            darkIcons = false
//        )
//
//        onDispose {
//            // kuch nahi – next screen handle karega
//        }
//    }
    val domain = viewModel.domain
    val state = viewModel.uiState


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
    ) {

        Toolbar(
            title = stringResource(R.string.batch_list),
            domain = domain,
            onBack = onBack
        )

        when{
            state.isLoading -> {
                BatchListShimmer()
            }
            state.batches.isEmpty() -> {
                Log.e("BatchList", "BatchListScreen: Empty")
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(dimens.spaceM),
                    verticalArrangement = Arrangement.spacedBy(dimens.spaceS)
                ) {
                    items(state.batches) { batch ->
                        BatchItem(
                            batch,
                            onClick = {
                                selectedBatchId = batch.batchId
                                showAttendanceSheet = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showAttendanceSheet) {
        AttendanceOptionBottomSheet(
            domain = domain,
            onDismiss = {
                showAttendanceSheet = false
                selectedBatchId = null
                        },
            onCandidateAttendance = {
                showAttendanceSheet = false
                selectedScreen = "candidateList"
                // navigate to candidate list screen
                onCandidateList(selectedBatchId!!)
                selectedBatchId = null
            },
            onSelfAttendance = {
                showAttendanceSheet = false
                selectedScreen = "selfAttendance"
                // navigate to self attendance screen
                onMarkAttendance(selectedBatchId!!)
                selectedBatchId = null
            }
        )
    }
}
