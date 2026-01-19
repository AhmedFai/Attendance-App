package com.example.attendance.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.usecase.auth.GetLoginSessionUseCase
import com.example.attendance.domain.usecase.auth.LogoutUseCase
import com.example.attendance.domain.usecase.domain.GetSelectedDomainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getDomain: GetSelectedDomainUseCase,
    getSession: GetLoginSessionUseCase,
    private val clearSession: LogoutUseCase
): ViewModel() {

    var domain by mutableStateOf(DomainType.DDUGKY)
        private set

    var userName by mutableStateOf("Prashant Gupta")
        private set

    var email by mutableStateOf("prashant@example.com")
        private set

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

        viewModelScope.launch {
            getSession().collect { session ->
                userName = userName
                email = email
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            isLoggingOut = true
            delay(300)
            clearSession()
        }
    }

}