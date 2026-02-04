package com.example.attendance.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.usecase.auth.GetLoginSessionUseCase
import com.example.attendance.domain.usecase.faculty.GetFacultyProfileUseCase
import com.example.attendance.presentation.navGraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getSession: GetLoginSessionUseCase,
    private val getFacultyProfileUseCase: GetFacultyProfileUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<MainUiEvent>()
    val uiEvent: SharedFlow<MainUiEvent> = _uiEvent

    val uiState = getSession()
        .map { session ->
            if (session?.isLoggedIn == true) {
                val faculty = getFacultyProfileUseCase()
                if (faculty != null) {
                    _uiEvent.emit(MainUiEvent.ShowToast("Welcome " + getFacultyProfileUseCase()?.facultyName))
                }
                Route.HomeScreen.routeName
            } else {
                Route.LoginScreen.routeName
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null
        )

}