package com.dord.offlineattendance.presentation.changeLanguageScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dord.offlineattendance.domain.model.DomainType
import com.dord.offlineattendance.domain.usecase.changeLanguage.GetLanguageUseCase
import com.dord.offlineattendance.domain.usecase.changeLanguage.SetLanguageUseCase
import com.dord.offlineattendance.domain.usecase.domain.GetSelectedDomainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeLanguageViewModel @Inject constructor(
    getDomain: GetSelectedDomainUseCase,
    private val getLanguage: GetLanguageUseCase,
    private val setLanguage: SetLanguageUseCase
): ViewModel() {
    var domain by mutableStateOf(DomainType.RSETI)
        private set

    private val _language = MutableStateFlow("en")
    val language: StateFlow<String> = _language

    init {
        viewModelScope.launch {
            getDomain().collect { domain = it }
        }
        viewModelScope.launch {
            getLanguage().collect {
                _language.value = it
            }
        }
    }

    fun changeLanguage(code: String) {
        viewModelScope.launch {
            setLanguage(code)
            _language.value = code
        }
    }
}