package com.example.attendance.domain.usecase.changeLanguage

import com.example.attendance.domain.repository.LanguageRepository

class SetLanguageUseCase(
    private val repo: LanguageRepository
) {
    suspend operator fun invoke(code: String) {
        repo.setLanguage(code)
    }
}