package com.dord.offlineattendance.domain.usecase.changeLanguage

import com.dord.offlineattendance.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow

class GetLanguageUseCase(
    private val repo: LanguageRepository
) {
    operator fun invoke(): Flow<String> = repo.getLanguage()
}