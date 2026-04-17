package com.dord.offlineattendance.presentation.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.dord.offlineattendance.R
import com.dord.offlineattendance.domain.model.DomainType
import com.dord.offlineattendance.domain.model.SyncAttendanceResult
import com.dord.offlineattendance.domain.repository.NetworkChecker
import com.dord.offlineattendance.domain.usecase.attendance.ClearAttendanceUseCase
import com.dord.offlineattendance.domain.usecase.attendance.GetPendingCountUseCase
import com.dord.offlineattendance.domain.usecase.attendance.GetSyncedCountUseCase
import com.dord.offlineattendance.domain.usecase.attendance.SyncAttendanceUseCase
import com.dord.offlineattendance.domain.usecase.auth.LogoutAuthUseCase
import com.dord.offlineattendance.domain.usecase.auth.LogoutUseCase
import com.dord.offlineattendance.domain.usecase.batch.ClearBatchUseCase
import com.dord.offlineattendance.domain.usecase.batch.GetCandidateIdsByBatchUseCase
import com.dord.offlineattendance.domain.usecase.candidate.ClearCandidateUseCase
import com.dord.offlineattendance.domain.usecase.candidate.GetCandidatesListUseCase
import com.dord.offlineattendance.domain.usecase.domain.GetSelectedDomainUseCase
import com.dord.offlineattendance.domain.usecase.faculty.ClearFacultyUseCase
import com.dord.offlineattendance.domain.usecase.faculty.GetFacultyProfileUseCase
import com.dord.offlineattendance.presentation.home.HomeUIEvent.*
import com.dord.offlineattendance.util.ApiState
import com.dord.offlineattendance.util.UiText.*
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
    private val logoutApiUseCase: LogoutAuthUseCase,
    private val networkChecker: NetworkChecker,
    private val getCandidateIdsByBatchUseCase: GetCandidateIdsByBatchUseCase
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

    fun onLogoutClick() {
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

    private suspend fun performLogout() {


        //uiState = uiState.copy(isLoggingOut = true)

//        val result = logoutApiUseCase()
//
//        if (result.isFailure) {
//            uiState = uiState.copy(isLoggingOut = false)
//            _uiEvent.emit(
//                HomeUIEvent.ShowToast(UiText.Dynamic("Logout failed. Try again."))
//            )
//            return
//        }

        logoutApiUseCase().collect { result ->
            when(result){
                is ApiState.Loading -> {
                    uiState = uiState.copy(isSyncing = true)
                    delay(3000)
                }
                is ApiState.Success -> {
                    uiState = uiState.copy(isSyncing = false)
                    clearSession()
                    clearCandidateUseCase()
                    clearBatchUseCase()
                    clearAttendanceUseCase()
                    clearFacultyUseCase()

                    WorkManager.getInstance(context)
                        .cancelUniqueWork("attendance_sync_periodic")

                    isLoggingOut = true
                    _uiEvent.emit(
                        ShowToast(Dynamic(result.data.responseDesc))
                    )
                }
                is ApiState.Error -> {
                    uiState = uiState.copy(isSyncing = false)
                    isLoggingOut = false
                    _uiEvent.emit(
                        ShowToast(Dynamic(result.message))
                    )
                }
                is ApiState.Exception -> {
                    uiState = uiState.copy(isSyncing = false)
                    isLoggingOut = false
                    _uiEvent.emit(
                        ShowToast(Dynamic("Something went wrong"))
                    )
                }
            }
        }

//        delay(300)
//        clearSession()
//        clearCandidateUseCase()
//        clearBatchUseCase()
//        clearAttendanceUseCase()
//        clearFacultyUseCase()
//        WorkManager.getInstance(context)
//            .cancelUniqueWork("attendance_sync_periodic")
    }

    fun getCandidateIdsForBatch(batchId: Long, onResult: (List<String>) -> Unit) {
        viewModelScope.launch {
            getCandidateIdsByBatchUseCase(batchId).collect { candidates ->
                onResult(candidates)
            }
        }
    }

}