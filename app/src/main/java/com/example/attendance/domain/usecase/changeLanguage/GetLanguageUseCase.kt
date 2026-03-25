package com.example.attendance.domain.usecase.changeLanguage

import com.example.attendance.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow

class GetLanguageUseCase(
    private val repo: LanguageRepository
) {
    operator fun invoke(): Flow<String> = repo.getLanguage()
}