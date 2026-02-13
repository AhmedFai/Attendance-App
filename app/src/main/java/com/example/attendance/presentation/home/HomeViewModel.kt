package com.example.attendance.presentation.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.attendance.R
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.model.SyncAttendanceResult
import com.example.attendance.domain.repository.NetworkChecker
import com.example.attendance.domain.usecase.attendance.ClearAttendanceUseCase
import com.example.attendance.domain.usecase.attendance.GetPendingCountUseCase
import com.example.attendance.domain.usecase.attendance.GetSyncedCountUseCase
import com.example.attendance.domain.usecase.attendance.SyncAttendanceUseCase
import com.example.attendance.domain.usecase.auth.LogoutUseCase
import com.example.attendance.domain.usecase.batch.ClearBatchUseCase
import com.example.attendance.domain.usecase.candidate.ClearCandidateUseCase
import com.example.attendance.domain.usecase.candidate.GetCandidatesListUseCase
import com.example.attendance.domain.usecase.domain.GetSelectedDomainUseCase
import com.example.attendance.domain.usecase.faculty.ClearFacultyUseCase
import com.example.attendance.domain.usecase.faculty.GetFacultyProfileUseCase
import com.example.attendance.presentation.home.HomeUIEvent.*
import com.example.attendance.util.UiText
import com.example.attendance.util.UiText.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    getDomain: GetSelectedDomainUseCase,
    private val clearSession: LogoutUseCase,
    private val getFacultyProfileUseCase: GetFacultyProfileUseCase,
    private val clearCandidateUseCase: ClearCandidateUseCase,
    private val clearBatchUseCase: ClearBatchUseCase,
    private val clearAttendanceUseCase: ClearAttendanceUseCase,
    private val clearFacultyUseCase: ClearFacultyUseCase,
    private val getPendingCountUseCase: GetPendingCountUseCase,
    private val getSyncedCountUseCase: GetSyncedCountUseCase,
    private val getCandidatesListUseCase: GetCandidatesListUseCase,
    private val getSyncAttendanceUseCase: SyncAttendanceUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    private val _uiEvent = MutableSharedFlow<HomeUIEvent>()
    val uiEvent: SharedFlow<HomeUIEvent> = _uiEvent


    var domain by mutableStateOf(DomainType.DDUGKY)
        private set

    var isLoggingOut by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            getDomain().collect {
                domain = it
            }
        }
        loadHomeData()
    }

    private fun loadHomeData() = viewModelScope.launch {
        uiState = uiState.copy(isLoading = true)
        delay(1000)
        val faculty = getFacultyProfileUseCase()
        val pending = getPendingCountUseCase()
        val synced = getSyncedCountUseCase()
        val candidates = getCandidatesListUseCase()
        uiState = if (faculty != null) {
            HomeUiState(
                isLoading = false,
                userName = faculty.facultyName,
                email = faculty.emailId,
                gender = faculty.gender,
                userId = faculty.loginId,
                pendingCount = pending,
                syncedCount = synced,
                candidatesId = candidates
            )
        } else {
            uiState.copy(isLoading = false)
        }
    }

    fun onLogoutClick(){
        uiState = uiState.copy(showLogoutDialog = true)
    }

    fun onDialogDismiss() {
        uiState = uiState.copy(
            showLogoutDialog = false,
            showSyncDialog = false
        )
    }

    fun syncAttendance() {
        viewModelScope.launch {
            uiState = uiState.copy(isSyncing = true)
            delay(5000)
            when (val result = getSyncAttendanceUseCase()) {
                is SyncAttendanceResult.Error -> {
                    uiState = uiState.copy(
                        isSyncing = false
                    )
                    _uiEvent.emit(ShowToast(Dynamic(result.message)))
                }

                SyncAttendanceResult.Loading -> {
                    uiState = uiState.copy(
                        isSyncing = true
                    )
                }

                SyncAttendanceResult.NoInternet -> {
                    uiState = uiState.copy(
                        isSyncing = false
                    )
                    _uiEvent.emit(ShowToast(StringRes(R.string.noInternetConnection)))
                }

                is SyncAttendanceResult.Success -> {
                    refreshCounts()
                    uiState = uiState.copy(
                        isSyncing = false
                    )
                    _uiEvent.emit(ShowToast(StringRes(R.string.syncedSuccessfully)))
                }

                SyncAttendanceResult.NoPendingData -> {
                    uiState = uiState.copy(
                        isSyncing = false
                    )
                    _uiEvent.emit(ShowToast(StringRes(R.string.pendingAttendance)))
                }
            }
        }
    }

    fun refreshCounts() {
        viewModelScope.launch {
            val pending = getPendingCountUseCase()
            val synced = getSyncedCountUseCase()
            uiState = uiState.copy(
                pendingCount = pending,
                syncedCount = synced
            )
        }
    }

    fun confirmLogout() =
        viewModelScope.launch {
            uiState = uiState.copy(showLogoutDialog = false)
            if (!networkChecker.isConnected()) {
                _uiEvent.emit(
                    ShowToast(StringRes(R.string.netRequired))
                )
                return@launch
            }
            if (getPendingCountUseCase() > 0) {
//                _uiEvent.emit(
//                    ShowToast(StringRes(R.string.syncRequired))
//                )
//                syncAttendance()
                uiState = uiState.copy(showSyncDialog = true)
                return@launch
            }
            performLogout()
        }

    fun syncAndLogout() = viewModelScope.launch {

        uiState = uiState.copy(showSyncDialog = false, isSyncing = true)

        when (getSyncAttendanceUseCase()) {

            SyncAttendanceResult.Success -> {
                uiState = uiState.copy(isSyncing = false)
                performLogout()
            }

            SyncAttendanceResult.NoPendingData -> {
                uiState = uiState.copy(isSyncing = false)
                performLogout()
            }

            else -> {
                uiState = uiState.copy(isSyncing = false)
                _uiEvent.emit(
                    ShowToast(StringRes(R.string.syncFailed))
                )
            }
        }
    }

    private suspend fun performLogout(){


        //uiState = uiState.copy(isLoggingOut = true)

//        val result = logoutApiUseCase()   // ⭐ LOGOUT API CALL
//
//        if (result.isFailure) {
//            uiState = uiState.copy(isLoggingOut = false)
//            _uiEvent.emit(
//                HomeUIEvent.ShowToast(UiText.Dynamic("Logout failed. Try again."))
//            )
//            return
//        }

        isLoggingOut = true
        delay(300)
        clearSession()
        clearCandidateUseCase()
        clearBatchUseCase()
        clearAttendanceUseCase()
        clearFacultyUseCase()
        WorkManager.getInstance(context)
            .cancelUniqueWork("attendance_sync_periodic")
    }

}