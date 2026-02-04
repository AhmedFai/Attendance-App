package com.example.attendance.presentation.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.attendance.R
import com.example.attendance.domain.model.DomainType
import com.example.attendance.presentation.home.shimmer.HomeShimmer
import com.example.attendance.ui.theme.dimens
import com.example.attendance.util.Constants
import com.google.gson.Gson
import com.pehchaan.backend.service.AuthenticationActivity

@Composable
fun HomeScreen(
//    domain: DomainType,
//    userName: String,
//    email: String,
//    onLogout: () -> Unit,
//    onMarkAttendance: () -> Unit,
//    onOfflineData: () -> Unit,
//    onFetchEmbeddings: () -> Unit,
//    onSync: () -> Unit

    viewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit,
    onMarkAttendance: () -> Unit
) {

    val uiState = viewModel.uiState
    val isLoggingOut = viewModel.isLoggingOut
    val dimens = MaterialTheme.dimens
    val context = LocalContext.current
    val fetchEmbeddingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        handleFetchEmbeddingsResult(result, context)
    }
    val json = Gson().toJson(uiState.candidatesId + uiState.userId)

    LaunchedEffect(isLoggingOut) {
        if (isLoggingOut) {
            onLogout()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HomeUIEvent.ShowToast -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    AnimatedVisibility(
        visible = !isLoggingOut,
        exit = fadeOut() + scaleOut()
    ) {

        if (uiState.isLoading) {
            HomeShimmer()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
                    .padding(bottom = dimens.screenPaddingVertical)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(0, 0, 15, 15))
                            .height(dimens.homeBackgroundBox)
                            .background(
                                Brush.verticalGradient(viewModel.domain.gradient)
                            )
                    )

                    // 🧱 SOLID BACKGROUND BELOW
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = dimens.homeBackgroundBox)
                            .background(Color.White)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = dimens.spaceL),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(Modifier.height(dimens.space3XL))

                        Box(
                            modifier = Modifier
                                .height(dimens.logoHeight)
                                .width(dimens.logoWidth)
                                .padding(dimens.spaceS)
                                .clip(RoundedCornerShape(dimens.spaceXS))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(
                                    if (viewModel.domain == DomainType.DDUGKY)
                                        R.drawable.ic_ddgky
                                    else
                                        R.drawable.ic_rseti
                                ),
                                contentDescription = null,
                                modifier = Modifier.height(dimens.space2XL)
                            )
                        }

                        Spacer(Modifier.height(dimens.spaceL))

                        // 👤 PROFILE CARD
                        ProfileCard(
                            domain = viewModel.domain,
                            userName = viewModel.uiState.userName,
                            email = viewModel.uiState.email,
                            gender = viewModel.uiState.gender,
                            onLogout = {
                                viewModel.logout()
                            }
                        )

                        Spacer(Modifier.height(dimens.spaceL))

                        // 🔲 ACTION GRID
                        ActionGrid(
                            domain = viewModel.domain,
                            pendingCount = viewModel.uiState.pendingCount,
                            syncedCount = viewModel.uiState.syncedCount,
                            onMarkAttendance = { onMarkAttendance() },
                            onOfflineData = {},
                            onFetchEmbeddings = {
                                fetchUserEmbeddings(context, json, fetchEmbeddingsLauncher)
                            },
                            onSync = {
                                viewModel.syncAttendance()
                            },
                            onTotalSync = {

                            }
                        )

                    }
                }
            }
        }
    }

}

private fun fetchUserEmbeddings(
    context: Context,
    json: String,
    launcher: ActivityResultLauncher<Intent>,
) {
    val intent = Intent(context, AuthenticationActivity::class.java).apply {
        putExtra(Constants.EXTRA_CLIENT_ID, Constants.YOUR_CLIENT_ID)
        putExtra(Constants.EXTRA_FETCH_USER_EMBEDDING, true)
        putExtra(Constants.EXTRA_USER_IDS, json)
    }
    try {
        launcher.launch(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Fetch embeddings service unavailable", Toast.LENGTH_SHORT).show()
    }
}

private fun handleFetchEmbeddingsResult(result: ActivityResult, context: Context) {
    if (result.resultCode == Activity.RESULT_OK){
        val status = result.data?.getStringExtra(Constants.RESULT_STATUS) ?: "failure"
        val message = result.data?.getStringExtra(Constants.RESULT_MESSAGE) ?: "Unknown error"
        val fetchedCount = result.data?.getIntExtra("fetched_count", 0) ?: 0
        when(status){
            "success" -> {
                Toast.makeText(context, "Embeddings fetched for : $fetchedCount users", Toast.LENGTH_SHORT).show()
            }
            "partial_success" -> {
                Toast.makeText(context, "Partially succeeded $message", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "Failed to fetch embeddings: $message", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}