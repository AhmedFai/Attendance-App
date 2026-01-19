package com.example.attendance.presentation.batchListScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.model.BatchData
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.usecase.domain.GetSelectedDomainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BatchListViewModel @Inject constructor(
    getDomain: GetSelectedDomainUseCase
): ViewModel() {
    var domain by mutableStateOf(DomainType.RSETI)
        private set

    val batches = listOf(
        BatchData("1024", "Web Development Training"),
        BatchData("1035", "Digital Marketing Course"),
        BatchData("1042", "Accounting & Finance Basics"),
        BatchData("1050", "Graphic Design Workshop"),
        BatchData("1051", "Retail Management")
    )

    init {
        viewModelScope.launch {
            getDomain().collect { domain = it }
        }
    }
}