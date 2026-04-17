package com.dord.offlineattendance.domain.usecase.candidate

import com.dord.offlineattendance.domain.repository.CandidateMasterDataRepository
import javax.inject.Inject

class CandidateMasterDataUseCase @Inject constructor(
    val repo: CandidateMasterDataRepository
) {

    operator fun invoke() = repo.getUserMasterData()

}