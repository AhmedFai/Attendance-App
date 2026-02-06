package com.example.attendance.presentation.candidateListScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.usecase.candidate.GetCandidatesByBatchUseCase
import com.example.attendance.domain.usecase.domain.GetSelectedDomainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CandidateListViewModel @Inject constructor(
    getDomain: GetSelectedDomainUseCase,
    private val getCandidatesByBatchUseCase: GetCandidatesByBatchUseCase
) : ViewModel() {

    var uiState by mutableStateOf(CandidateListUiState())
        private set

    var domain by mutableStateOf(DomainType.RSETI)
        private set

    init {
        viewModelScope.launch {
            getDomain().collect { domain = it }
        }
    }

    fun loadCandidates(batchId: Long){
        if (uiState.hasLoadedOnce) return
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(1000)
            getCandidatesByBatchUseCase(batchId).collect { candidates ->
                uiState = uiState.copy(
                    isLoading = false,
                    candidates = candidates,
                    hasLoadedOnce = true
                )
            }
        }
    }
}