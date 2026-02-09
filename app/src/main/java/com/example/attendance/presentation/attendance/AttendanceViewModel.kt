package com.example.attendance.presentation.attendance

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.data.local.entity.AttendanceEntity
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.usecase.attendance.GetCandidateByIdUseCase
import com.example.attendance.domain.usecase.attendance.GetCheckInTimeUseCase
import com.example.attendance.domain.usecase.attendance.GetCheckOutTimeUseCase
import com.example.attendance.domain.usecase.attendance.IsCheckedInTodayUseCase
import com.example.attendance.domain.usecase.attendance.IsCheckedOutTodayUseCase
import com.example.attendance.domain.usecase.attendance.MarkCheckInUseCase
import com.example.attendance.domain.usecase.attendance.MarkCheckOutUseCase
import com.example.attendance.domain.usecase.batch.GetBatchByIdUseCase
import com.example.attendance.domain.usecase.domain.GetSelectedDomainUseCase
import com.example.attendance.domain.usecase.faculty.GetFacultyProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    getDomain: GetSelectedDomainUseCase,
    private val getCandidateByIdUseCase: GetCandidateByIdUseCase,
    private val getFacultyProfileUseCase: GetFacultyProfileUseCase,
    private val getBatchByIdUseCase: GetBatchByIdUseCase,
    private val markCheckInUseCase: MarkCheckInUseCase,
    private val markCheckOutUseCase: MarkCheckOutUseCase,
    private val getCheckInTimeUseCase: GetCheckInTimeUseCase,
    private val getCheckOutTimeUseCase: GetCheckOutTimeUseCase,
    private val isCheckedInTodayUseCase: IsCheckedInTodayUseCase,
    private val isCheckedOutTodayUseCase: IsCheckedOutTodayUseCase
) : ViewModel() {

    private var _uiState by mutableStateOf(AttendanceUiState())
    val uiState: AttendanceUiState get() = _uiState


    var domain by mutableStateOf(DomainType.RSETI)
        private set

    init {
        viewModelScope.launch {
            getDomain().collect { domain = it }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadUser(userType: String, userId: String, batchId: Long){
        Log.e("USER", "USER_ID: $userId, USER_TYPE: $userType")
       // if (_uiState.hasLoadedOnce) return
        viewModelScope.launch {
            _uiState = _uiState.copy(isLoading = true)

            when(userType){
                "FACULTY" -> {
                    val faculty = getFacultyProfileUseCase()

                    if (faculty != null) {
                        _uiState = _uiState.copy(
                            faculty = faculty,
                            isLoading = false
                        )

                        loadBatchData(batchId)
                        loadTodayAttendance(userId, batchId, "FACULTY")
                    } else {
                        _uiState = _uiState.copy(
                            isLoading = false,
                            error = "Faculty not found"
                        )
                    }
                }
                "CANDIDATE" -> {
                    val candidate = getCandidateByIdUseCase(userId)
                    if (candidate != null){
                       _uiState = _uiState.copy(
                            candidate = candidate,
                            isLoading = false
                        )
                        loadBatchData(batchId)
                        loadTodayAttendance(userId, batchId, "CANDIDATE")
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

   private fun loadBatchData(batchId: Long){
        viewModelScope.launch {
            val batch = getBatchByIdUseCase(batchId)
            _uiState = if (batch != null){
                _uiState.copy(
                    isLoading = false,
                    batch = batch
                )
            }else {
                _uiState.copy(
                    isLoading = false,
                    error = "Batch not found"
                )
            }
            Log.e("BATCH_ID", batch?.batchId.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadTodayAttendance(
        userId: String,
        batchId: Long,
        userType: String
    ){
        viewModelScope.launch {
            val todayDate = java.time.LocalDate.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))

            val checkedIn = isCheckedInTodayUseCase(userId, userType, batchId, todayDate)
            val checkedOut = isCheckedOutTodayUseCase(userId, userType, batchId, todayDate)

            val checkInTime = getCheckInTimeUseCase(userId, userType, batchId, todayDate)
            val checkOutTime = getCheckOutTimeUseCase(userId, userType, batchId, todayDate)

            // total hours calculate
            val totalHours = if (checkInTime != null && checkOutTime != null) {
                val inTime = java.time.LocalTime.parse(checkInTime)
                val outTime = java.time.LocalTime.parse(checkOutTime)

                val duration = java.time.Duration.between(inTime, outTime)
                val hours = duration.toHours()
                val minutes = duration.toMinutes() % 60

                String.format("%02d:%02d hrs", hours, minutes)
            } else {
                "00:00 hrs"
            }

            // button rules
            val canCheckIn = !checkedIn
            val canCheckOut = checkedIn && !checkedOut

            _uiState = _uiState.copy(
                isCheckedInToday = checkedIn,
                isCheckedOutToday = checkedOut,
                checkInTime = checkInTime,
                checkOutTime = checkOutTime,
                totalHours = totalHours,
                canCheckIn = canCheckIn,
                canCheckOut = canCheckOut
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun markCheckIn(
        userId: String,
        userType: String,
        batchId: Long,
        latitude: Double?,
        longitude: Double?
    ) {
        viewModelScope.launch {

            val currentDate = java.time.LocalDate.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))

            val currentTime = java.time.LocalTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"))

            val entity = AttendanceEntity(
                userId = userId,
                userType = userType,
                batchId = batchId,
                attendanceDate = currentDate,
                checkIn = currentTime,
                checkOut = null,
                totalHours = null,
                address = null, // later
                capturedLatitude = latitude,
                capturedLongitude = longitude,
                syncStatus = "PENDING"
            )

            markCheckInUseCase(entity)
            _uiState = _uiState.copy(
                showSuccessSheet = true,
                successType = "CHECK-IN"
            )
            loadTodayAttendance(userId, batchId, userType)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun markCheckOut(
        userId: String,
        batchId: Long,
        userType: String
    ) {
        viewModelScope.launch {

            val currentDate = java.time.LocalDate.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))

            val checkInTime = getCheckInTimeUseCase(userId, userType, batchId, currentDate)
                ?: return@launch

            val outTime = java.time.LocalTime.now()

            val formattedOut = outTime.format(
                java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")
            )

            val inTime = java.time.LocalTime.parse(checkInTime)

            val duration = java.time.Duration.between(inTime, outTime)

            val hours = duration.toHours()
            val minutes = duration.toMinutes() % 60

            val totalHours = String.format("%02d:%02d", hours, minutes)

            markCheckOutUseCase(
                userId,
                userType,
                batchId,
                currentDate,
                formattedOut,
                totalHours
            )
            _uiState = _uiState.copy(
                showSuccessSheet = true,
                successType = "CHECK-OUT"
            )
            loadTodayAttendance(userId, batchId, userType)
        }
    }

    fun hideSuccessSheet() {
        _uiState = _uiState.copy(showSuccessSheet = false, successType = null)
    }

}
