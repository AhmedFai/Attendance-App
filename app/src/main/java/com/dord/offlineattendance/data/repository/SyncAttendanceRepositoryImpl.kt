package com.dord.offlineattendance.data.repository

import android.content.Context
import com.dord.offlineattendance.BuildConfig
import com.dord.offlineattendance.data.datastore.AppPreferences
import com.dord.offlineattendance.data.remote.api.ApiServices
import com.dord.offlineattendance.domain.model.DomainType
import com.dord.offlineattendance.domain.model.SyncAttendanceResult
import com.dord.offlineattendance.domain.model.candidateAttendanceData.CandidateAttendance
import com.dord.offlineattendance.domain.model.candidateAttendanceData.CandidateAttendanceRequest
import com.dord.offlineattendance.domain.model.facultyAttendanceData.FacultyAttendance
import com.dord.offlineattendance.domain.model.facultyAttendanceData.FacultyAttendanceRequest
import com.dord.offlineattendance.domain.repository.AttendanceRepository
import com.dord.offlineattendance.domain.repository.NetworkChecker
import com.dord.offlineattendance.domain.repository.SyncAttendanceRepository
import com.dord.offlineattendance.util.AppUtil
import com.dord.offlineattendance.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.net.ssl.SSLPeerUnverifiedException

class SyncAttendanceRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
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

            val syncedIds = mutableListOf<Long>()

            val candidatePending = pending.filter { it.userType == "CANDIDATE" }

            if (candidatePending.isNotEmpty()) {

                val candidateData = candidatePending.map {
                    CandidateAttendance(
                        imeiNo = AppUtil.getAndroidId(context),
                        attendanceDate = it.attendanceDate,
                        batchId = it.batchId.toString(),
                        candidateId = it.userId,
                        checkIn = it.checkIn,
                        checkOut = it.checkOut ?: "",
                        totalHours = it.totalHours ?: "",
                        address = "",
                        batchRegNo = it.batchRegNo
                    )
                }

                val res = api.syncCandidateAttendance(
                    candidateUrl,
                    CandidateAttendanceRequest(candidateData)
                )

                if (res.isSuccessful && res.body()?.responseCode == 200) {
                    syncedIds += candidatePending.map { it.id }
                } else {
                    return SyncAttendanceResult.Error("Candidate attendance sync failed")
                }
            }

            val facultyPending = pending.filter { it.userType == "FACULTY" }

            if (facultyPending.isNotEmpty()) {

                val facultyData = facultyPending.map {
                    FacultyAttendance(
                        imeiNo = AppUtil.getAndroidId(context),
                        attendanceDate = it.attendanceDate,
                        batchId = it.batchId.toString(),
                        checkIn = it.checkIn,
                        checkOut = it.checkOut ?: "",
                        totalHours = it.totalHours ?: "",
                        address = "",
                        login = it.userId,
                        batchRegNo = it.batchRegNo
                    )
                }

                val res = api.syncFacultyAttendance(
                    facultyUrl,
                    FacultyAttendanceRequest(facultyData)
                )

                if (res.isSuccessful && res.body()?.responseCode == 200) {
                    syncedIds += facultyPending.map { it.id }
                } else {
                    return SyncAttendanceResult.Error("Faculty attendance sync failed")
                }
            }

            if (syncedIds.isNotEmpty()) {
                repository.markAttendancesSynced(syncedIds)
            }

            return SyncAttendanceResult.Success
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            return when (e) {
                is SSLPeerUnverifiedException -> {
                    SyncAttendanceResult.Error("Security issue detected. Please use a secure network.")
                }

                else -> {
                    SyncAttendanceResult.Error(e.message.toString())
                }
            }
        }
    }
}