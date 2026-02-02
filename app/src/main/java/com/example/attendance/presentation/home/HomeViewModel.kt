package com.example.attendance.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.repository.NetworkChecker
import com.example.attendance.domain.usecase.attendance.ClearAttendanceUseCase
import com.example.attendance.domain.usecase.auth.GetLoginSessionUseCase
import com.example.attendance.domain.usecase.auth.LogoutUseCase
import com.example.attendance.domain.usecase.batch.ClearBatchUseCase
import com.example.attendance.domain.usecase.candidate.CandidateMasterDataUseCase
import com.example.attendance.domain.usecase.candidate.ClearCandidateUseCase
import com.example.attendance.domain.usecase.domain.GetSelectedDomainUseCase
import com.example.attendance.domain.usecase.faculty.ClearFacultyUseCase
import com.example.attendance.domain.usecase.faculty.FacultyMasterDataUseCase
import com.example.attendance.domain.usecase.faculty.GetFacultyProfileUseCase
import com.example.attendance.presentation.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getDomain: GetSelectedDomainUseCase,
    getSession: GetLoginSessionUseCase,
    private val clearSession: LogoutUseCase,
    private val getFacultyProfileUseCase: GetFacultyProfileUseCase,
    private val clearCandidateUseCase: ClearCandidateUseCase,
    private val clearBatchUseCase: ClearBatchUseCase,
    private val clearAttendanceUseCase: ClearAttendanceUseCase,
    private val clearFacultyUseCase: ClearFacultyUseCase
): ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    var domain by mutableStateOf(DomainType.DDUGKY)
        private set

//    var userName by mutableStateOf("Prashant Gupta")
//        private set
//
//    var email by mutableStateOf("prashant@example.com")
//        private set

    var isLoggingOut by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            getDomain().collect {
                domain = it
            }
        }

//        viewModelScope.launch {
//            getSession().collect { session ->
//                if (session != null) {
//                    userName = session.userId
//                    email = "${session.userId}@example.com"
//                }
//            }
//        }

//        viewModelScope.launch {
//            getSession().collect { session ->
//                userName = userName
//                email = email
//            }
//        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(1000)
            val faculty = getFacultyProfileUseCase()
            if (faculty != null){
                uiState = HomeUiState(
                    isLoading = false,
                    userName = faculty.facultyName,
                    email = faculty.emailId
                )
            } else {
                uiState = uiState.copy(isLoading = false)
            }
        }

    }

    fun logout() {
        viewModelScope.launch {
            isLoggingOut = true
            delay(300)
            clearSession()
            clearCandidateUseCase()
            clearBatchUseCase()
            clearAttendanceUseCase()
            clearFacultyUseCase()
        }
    }

}