package com.example.attendance.presentation.attendance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.model.AttendanceStatus
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.usecase.attendance.GetCandidateByIdUseCase
import com.example.attendance.domain.usecase.domain.GetSelectedDomainUseCase
import com.example.attendance.domain.usecase.faculty.GetFacultyProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    getDomain: GetSelectedDomainUseCase,
    private val getCandidateByIdUseCase: GetCandidateByIdUseCase,
    private val getFacultyProfileUseCase: GetFacultyProfileUseCase
) : ViewModel() {

    private var _uiState by mutableStateOf(AttendanceUiState())
    val uiState: AttendanceUiState get() = _uiState


    var domain by mutableStateOf(DomainType.RSETI)
        private set

    var attendanceStatus by mutableStateOf(AttendanceStatus.NONE)
        private set

    fun onCheckIn() {
        attendanceStatus = AttendanceStatus.CHECKED_IN
    }

    fun onCheckOut() {
        attendanceStatus = AttendanceStatus.CHECKED_OUT
    }

    init {
        viewModelScope.launch {
            getDomain().collect { domain = it }
        }
    }

    fun loadUser(userType: String, userId: String){
        if (_uiState.hasLoadedOnce) return
        viewModelScope.launch {
            _uiState = _uiState.copy(isLoading = true)

            when(userType){
                "FACULTY" -> {
                    val faculty = getFacultyProfileUseCase()
                    _uiState = _uiState.copy(
                       faculty = faculty,
                        isLoading = false,
                        hasLoadedOnce = true
                    )
                }
                "CANDIDATE" -> {
                    val candidate = getCandidateByIdUseCase(userId)
                    _uiState = if (candidate != null){
                        _uiState.copy(
                            candidate = candidate,
                            isLoading = false,
                            hasLoadedOnce = true
                        )
                    }else {
                        _uiState.copy(
                            isLoading = false,
                            error = "Candidate not found"
                        )
                    }
                }
            }
        }
    }

}