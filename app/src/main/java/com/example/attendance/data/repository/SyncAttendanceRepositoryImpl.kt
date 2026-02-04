package com.example.attendance.data.repository

import android.content.SyncResult
import com.example.attendance.data.datastore.AppPreferences
import com.example.attendance.data.remote.api.ApiServices
import com.example.attendance.data.remote.api.LoginApiService
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.model.SyncAttendanceResult
import com.example.attendance.domain.model.candidateAttendanceData.CandidateAttendance
import com.example.attendance.domain.model.candidateAttendanceData.CandidateAttendanceRequest
import com.example.attendance.domain.model.facultyAttendanceData.FacultyAttendance
import com.example.attendance.domain.model.facultyAttendanceData.FacultyAttendanceRequest
import com.example.attendance.domain.repository.AttendanceRepository
import com.example.attendance.domain.repository.NetworkChecker
import com.example.attendance.domain.repository.SyncAttendanceRepository
import com.example.attendance.util.Constants
import javax.inject.Inject

class SyncAttendanceRepositoryImpl @Inject constructor(
    private val repository: AttendanceRepository,
    private val api: ApiServices,
    private val networkChecker: NetworkChecker,
    private val prefs: AppPreferences
) : SyncAttendanceRepository {
    override suspend fun syncPendingAttendances(): SyncAttendanceResult {
        try {
            val domain = prefs.getSelectedDomain()

            val baseUrl = when (domain) {
                DomainType.RSETI -> Constants.RSETI
                DomainType.DDUGKY -> Constants.DDUGKY
            }

            val candidateUrl = baseUrl + "insertOfflineAttendance"
            val facultyUrl = baseUrl + "insertOfflineFacultyAttendance"

            if (!networkChecker.isConnected()) {
                return SyncAttendanceResult.NoInternet
            }
            val pending = repository.getPendingAttendances()
            if (pending.isEmpty()) return SyncAttendanceResult.NoPendingData

            val candidateData = pending
                .filter { it.userType == "CANDIDATE" }
                .map {
                    CandidateAttendance(
                        imeiNo = "eb197b9cab05dac2",
                        attendanceDate = it.attendanceDate,
                        batchId = it.batchId.toString(),
                        candidateId = it.userId,
                        checkIn = it.checkIn,
                        checkOut = it.checkOut,
                        totalHours = it.totalHours,
                        address = ""
                    )
                }

            val facultyData = pending
                .filter { it.userType == "FACULTY" }
                .map {
                    FacultyAttendance(
                        imeiNo = "eb197b9cab05dac2",
                        attendanceDate = it.attendanceDate,
                        batchId = it.batchId.toString(),
                        checkIn = it.checkIn,
                        checkOut = it.checkOut,
                        totalHours = it.totalHours,
                        address = "",
                        login = it.userId
                    )
                }

            if (candidateData.isNotEmpty()) {
                api.syncCandidateAttendance(candidateUrl,
                    CandidateAttendanceRequest(candidateData)
                )
                //if (res.responseCode == 301) return SyncResult.UpgradeRequired
            }

            if (facultyData.isNotEmpty()) {
                api.syncFacultyAttendance(facultyUrl,
                    FacultyAttendanceRequest(facultyData)
                )
                //if (res.responseCode == 301) return SyncResult.UpgradeRequired
            }

            repository.markAttendancesSynced(pending.map { it.id })

        }catch (e : Exception){
            e.printStackTrace()
            return SyncAttendanceResult.Error(e.message.toString())
        }

        return SyncAttendanceResult.Success
    }
}