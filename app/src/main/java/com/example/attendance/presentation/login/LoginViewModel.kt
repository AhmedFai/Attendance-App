package com.example.attendance.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.model.DomainType
import com.example.attendance.domain.model.UserSession
import com.example.attendance.domain.model.login.LoginRequest
import com.example.attendance.domain.usecase.LoginUseCase
import com.example.attendance.domain.usecase.auth.GetLoginSessionUseCase
import com.example.attendance.domain.usecase.auth.SaveLoginSessionUseCase
import com.example.attendance.domain.usecase.domain.GetSelectedDomainUseCase
import com.example.attendance.domain.usecase.domain.SaveDomainUseCase
import com.example.attendance.util.ApiState
import com.example.attendance.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    getDomain: GetSelectedDomainUseCase,
    private val saveDomain: SaveDomainUseCase,
    getSession: GetLoginSessionUseCase,
    private val saveSession: SaveLoginSessionUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    private val _loginUiEvent = MutableSharedFlow<LoginUiEvent?>()
    val loginUiEvent: SharedFlow<LoginUiEvent?> = _loginUiEvent

    var userId by mutableStateOf("")
        private set


    var selectedDomain by mutableStateOf(DomainType.RSETI)
        private set

    var session by mutableStateOf<UserSession?>(null)
        private set

    var password by mutableStateOf("")
        private set

    var passwordVisible by mutableStateOf(false)
        private set

    fun onDomainChange(domain: DomainType) {
        viewModelScope.launch { saveDomain(domain) }
    }

    fun onUserIdChange(value: String) {
        userId = value
        _loginUiState.value =
            _loginUiState.value.copy(userIdError = null)
    }

    fun onPasswordChange(value: String) {
        password = value
        _loginUiState.value =
            _loginUiState.value.copy(passwordError = null)
    }

    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    init {
        viewModelScope.launch {
            getDomain().collect { domain ->
                selectedDomain = domain
            }
        }
        viewModelScope.launch {
            getSession().collect { session = it }
        }
    }

    fun onLogin(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState()

            if (!validate()) return@launch
            loginUseCase(loginRequest).collect { state ->
                when (state) {
                    is ApiState.Error -> {
                        _loginUiState.value = LoginUiState(
                            error = UiText.Dynamic(state.message),
                            isLoading = false
                        )
                        _loginUiEvent.emit(
                            LoginUiEvent.ShowToast(UiText.Dynamic(state.message))
                        )
                    }
                    is ApiState.Exception -> {
                        _loginUiState.value = LoginUiState(
                            error = UiText.Dynamic(state.exception.message.toString()),
                            isLoading = false
                        )
                        _loginUiEvent.emit(
                            LoginUiEvent.ShowToast(
                                UiText.Dynamic(state.exception.message ?: "Exception"
                            ))
                        )
                    }
                    is ApiState.Loading -> {
                        _loginUiState.value = LoginUiState(isLoading = true)
                        delay(2000)
                    }
                    is ApiState.Success -> {
                        _loginUiState.value = LoginUiState(
                            data = state.data,
                            isLoading = false
                        )
                        _loginUiEvent.emit(
                            LoginUiEvent.ShowToast(UiText.StringRes(com.example.attendance.R.string.loginSuccess))
                        )
                        saveSession(UserSession(userId, state.data.accessToken, true))
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        var valid = true

        if (userId.isBlank()) {
            _loginUiState.value =
                _loginUiState.value.copy(
                    userIdError = UiText.StringRes(com.example.attendance.R.string.userIdRequired)
                )
            valid = false
        }

        if (password.isBlank()) {
            _loginUiState.value =
                _loginUiState.value.copy(
                    passwordError = UiText.StringRes(com.example.attendance.R.string.passwordRequired)
                )
            valid = false
        }

        return valid
    }

}