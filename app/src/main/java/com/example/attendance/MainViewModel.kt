package com.example.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.usecase.auth.GetLoginSessionUseCase
import com.example.attendance.presentation.navGraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getSession: GetLoginSessionUseCase
): ViewModel() {

    val uiState = getSession()
        .map { session ->
            if (session?.isLoggedIn == true)
                Route.HomeScreen.routeName
            else
                Route.LoginScreen.routeName
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null // 👈 IMPORTANT
        )

}