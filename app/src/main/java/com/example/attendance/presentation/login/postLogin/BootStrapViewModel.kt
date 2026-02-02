package com.example.attendance.presentation.login.postLogin

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.data.mapper.toBatchEntity
import com.example.attendance.data.mapper.toCandidateEntity
import com.example.attendance.domain.repository.NetworkChecker
import com.example.attendance.domain.usecase.auth.GetLoginSessionUseCase
import com.example.attendance.domain.usecase.auth.SaveLoginSessionUseCase
import com.example.attendance.domain.usecase.batch.InsertBatchesUseCase
import com.example.attendance.domain.usecase.candidate.CandidateMasterDataUseCase
import com.example.attendance.domain.usecase.candidate.InsertCandidatesUseCase
import com.example.attendance.domain.usecase.faculty.FacultyMasterDataUseCase
import com.example.attendance.domain.usecase.faculty.InsertFacultiesUseCase
import com.example.attendance.util.ApiState
import com.example.attendance.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class BootStrapViewModel @Inject constructor(
    private val candidateMasterDataUseCase: CandidateMasterDataUseCase,
    private val facultyMasterDataUseCase: FacultyMasterDataUseCase,
    private val networkChecker: NetworkChecker,
    private val insertBatchesUseCase: InsertBatchesUseCase,
    private val insertFacultiesUseCase: InsertFacultiesUseCase,
    private val insertCandidatesUseCase: InsertCandidatesUseCase,
    private val getSessionUseCase: GetLoginSessionUseCase,
    private val saveSessionUseCase: SaveLoginSessionUseCase
) : ViewModel(){

    var state by mutableStateOf<BootstrapState>(BootstrapState.Idle)
        private set

    fun startBootstrap(){
        viewModelScope.launch {
            if (!networkChecker.isConnected()){
                state = BootstrapState.Error(UiText.StringRes(com.example.attendance.R.string.noInternetConnection))
                return@launch
            }
            state = BootstrapState.Loading
            try {
                supervisorScope {

                    val candidateJob = async {
                        handleCandidateMasterData()
                    }

                    val facultyJob = async {
                        handleFacultyMasterData()
                    }

                    candidateJob.await()
                    facultyJob.await()
                }
                markBootstrapCompleted()
                state = BootstrapState.Success
            }catch (e : Exception){
                state = BootstrapState.Error(UiText.Dynamic(e.message.toString()))
            }
        }
    }

    private suspend fun handleCandidateMasterData() {

        Log.e("BootstrapVM", "Candidate API call started")

        val result = candidateMasterDataUseCase()
            .first { it is ApiState.Success || it is ApiState.Error || it is ApiState.Exception<*> }

        when (result) {

            is ApiState.Success -> {
                Log.e("BootstrapVM", "Candidate API success")

                val wrappedList = result.data.wrappedList.orEmpty()

                /* ---------------- BATCH INSERT ---------------- */

                val batches = wrappedList
                    .map { it.toBatchEntity() } // Batch mapper
                    .distinctBy { it.batchId }

                Log.e("BootstrapVM", "Batch count = ${batches.size}")

                if (batches.isNotEmpty()) {
                    insertBatchesUseCase(batches)
                    Log.e("BootstrapVM", "Batches inserted ✅")
                }

                /* ---------------- CANDIDATE INSERT ---------------- */

                val candidates = wrappedList
                    .map { it.toCandidateEntity() } // 👈 Candidate mapper

                Log.e("BootstrapVM", "Candidate count = ${candidates.size}")

                if (candidates.isNotEmpty()) {
                    insertCandidatesUseCase(candidates)
                    Log.e("BootstrapVM", "Candidates inserted ✅")
                }
            }

            is ApiState.Error -> {
                throw Exception(result.message)
            }

            is ApiState.Loading -> {
                Log.e("BootstrapVM", "Candidate API call loading")
            }
            is ApiState.Exception<*> -> {
                Log.e("BootstrapVM", "Candidate API error: ${result.data.toString()}")
            }
        }
    }

    private suspend fun handleFacultyMasterData() {

        Log.e("BootstrapVM", "Faculty API call started")

        val result = facultyMasterDataUseCase()
            .first {
                it is ApiState.Success ||
                        it is ApiState.Error ||
                        it is ApiState.Exception<*>
            }

        when (result) {

            is ApiState.Success -> {
                Log.e("BootstrapVM", "Faculty API success")

                val faculties = result.data
                    .wrappedList
                    .map { it.toBatchEntity() }

                Log.e(
                    "BootstrapVM",
                    "Mapped FacultyEntity count = ${faculties.size}"
                )

                if (faculties.isNotEmpty()) {
                    insertFacultiesUseCase(faculties)
                    Log.e("BootstrapVM", "Faculty data inserted ✅")
                }
            }

            is ApiState.Error -> {
                Log.e("BootstrapVM", "Faculty API error: ${result.message}")
                throw Exception(result.message)
            }

            is ApiState.Exception<*> -> {
                Log.e("BootstrapVM", "Faculty API exception: ${result.data.toString()}")
                throw Exception(result.data.toString())
            }

            else -> Unit
        }
    }

    private suspend fun markBootstrapCompleted() {

        val currentSession = getSessionUseCase().first()
            ?: return   // safety

        saveSessionUseCase(
            currentSession.copy(
                isLoggedIn = true   // 👈 NOW APP IS READY
            )
        )
    }

}