package com.example.attendance.presentation.attendance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.model.AttendanceStatus
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.model.attendanceModel.CandidateUiModel
import com.example.attendance.domain.model.attendanceModel.SelfUserUiModel
import com.example.attendance.domain.usecase.domain.GetSelectedDomainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    getDomain: GetSelectedDomainUseCase
) : ViewModel() {

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

    var candidate = CandidateUiModel(
        name = "Walvinder Singh",
        rollNo = "1",
        contact = "9231949074",
        email = "james.s.sherman@example-pet-store.com",
        gender = "Male",
        dob = "12/12/2003"
    )

    var selfUserUiModel = SelfUserUiModel(
        name = "Walvinder Singh",
        loginId = "FACS",
        contact = "9231949074",
        email = "william.henry.harrison@example-pet-store.com",
        gender = "Male",
        dob = "12/12/2003"
    )

    init {
        viewModelScope.launch {
            getDomain().collect { domain = it }
        }
    }

}