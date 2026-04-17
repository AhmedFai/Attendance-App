package com.dord.offlineattendance.domain.usecase.changeLanguage

import com.dord.offlineattendance.domain.repository.LanguageRepository

class SetLanguageUseCase(
    private val repo: LanguageRepository
) {
    suspend operator fun invoke(code: String) {
        repo.setLanguage(code)
    }
}