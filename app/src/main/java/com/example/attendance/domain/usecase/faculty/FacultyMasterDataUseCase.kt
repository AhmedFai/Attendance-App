package com.example.attendance.domain.usecase.faculty

import com.example.attendance.domain.repository.FacultyMasterDataRepository
import javax.inject.Inject

class FacultyMasterDataUseCase @Inject constructor(
    val repo: FacultyMasterDataRepository
) {

    suspend operator fun invoke() = repo.getFacultyMasterData()

}