package com.example.attendance.presentation.batchListScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.usecase.batch.GetActiveBatchesUseCase
import com.example.attendance.domain.usecase.domain.GetSelectedDomainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BatchListViewModel @Inject constructor(
    getDomain: GetSelectedDomainUseCase,
    private val getActiveBatchesUseCase: GetActiveBatchesUseCase
): ViewModel() {
    var domain by mutableStateOf(DomainType.RSETI)
        private set

    var uiState by mutableStateOf(BatchListUiState())
        private set

    init {
        viewModelScope.launch {
            getDomain().collect { domain = it }
        }


        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(1000)
            getActiveBatchesUseCase().collect { batches ->
                uiState = uiState.copy(
                    isLoading = false,
                    batches = batches
                )
            }

        }

    }
}