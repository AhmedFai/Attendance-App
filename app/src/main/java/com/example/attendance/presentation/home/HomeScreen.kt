package com.example.attendance.presentation.home

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.R
import com.example.attendance.domain.model.DomainType
import com.example.attendance.presentation.home.shimmer.HomeShimmer
import com.example.attendance.ui.theme.dimens

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

    LaunchedEffect(isLoggingOut) {
        if (isLoggingOut) {
            onLogout()
        }
    }

    AnimatedVisibility(
        visible = !isLoggingOut,
        exit = fadeOut() + scaleOut()
    ) {

        if (uiState.isLoading){
            HomeShimmer()
        }else{
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
                            onLogout = {
                                viewModel.logout()
                            }
                        )

                        Spacer(Modifier.height(dimens.spaceL))

                        // 🔲 ACTION GRID
                        ActionGrid(
                            domain = viewModel.domain,
                            onMarkAttendance = { onMarkAttendance() },
                            onOfflineData = {},
                            onFetchEmbeddings = {},
                            onSync = {}
                        )

                    }
                }
            }
        }
    }

}