package com.example.attendance.domain.usecase.candidate

import com.example.attendance.domain.repository.CandidateMasterDataRepository
import javax.inject.Inject

class CandidateMasterDataUseCase @Inject constructor(
    val repo: CandidateMasterDataRepository
) {

    operator fun invoke() = repo.getUserMasterData()

}