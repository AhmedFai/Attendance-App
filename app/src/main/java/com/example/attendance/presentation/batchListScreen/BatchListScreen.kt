package com.example.attendance.presentation.batchListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.attendance.R
import com.example.attendance.domain.model.DomainType
import com.example.attendance.presentation.candidateListScreen.AttendanceOptionBottomSheet
import com.example.attendance.presentation.common.Toolbar
import com.example.attendance.ui.theme.dimens
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BatchListScreen(
    viewModel: BatchListViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onCandidateList: () -> Unit,
    onMarkAttendance: () -> Unit
) {
    var showAttendanceSheet by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()
    val dimens = MaterialTheme.dimens
    var selectedScreen: String = ""

    DisposableEffect(viewModel.domain) {
        systemUiController.setStatusBarColor(
            color = viewModel.domain.primaryColor,
            darkIcons = false
        )

        onDispose {
            // kuch nahi – next screen handle karega
        }
    }
    val domain = viewModel.domain
    val batches = viewModel.batches


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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(dimens.spaceM),
            verticalArrangement = Arrangement.spacedBy(dimens.spaceS)
        ) {
            items(batches) { batch ->
                BatchItem(
                    batch,
                    onClick = {
                        showAttendanceSheet = true
                    }
                )
            }
        }
    }

    if (showAttendanceSheet) {
        AttendanceOptionBottomSheet(
            domain = domain,
            onDismiss = { showAttendanceSheet = false },
            onCandidateAttendance = {
                showAttendanceSheet = false
                selectedScreen = "candidateList"
                // navigate to candidate list screen
                onCandidateList()
            },
            onSelfAttendance = {
                showAttendanceSheet = false
                selectedScreen = "selfAttendance"
                // navigate to self attendance screen
                onMarkAttendance()
            }
        )
    }
}
