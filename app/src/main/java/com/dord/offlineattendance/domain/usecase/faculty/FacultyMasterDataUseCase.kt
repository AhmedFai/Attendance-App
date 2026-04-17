package com.dord.offlineattendance.domain.usecase.faculty

import com.dord.offlineattendance.domain.repository.FacultyMasterDataRepository
import javax.inject.Inject

class FacultyMasterDataUseCase @Inject constructor(
    val repo: FacultyMasterDataRepository
) {
    suspend operator fun invoke() = repo.getFacultyMasterData()
}