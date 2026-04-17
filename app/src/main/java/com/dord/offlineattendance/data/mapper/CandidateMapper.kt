package com.dord.offlineattendance.data.mapper

import com.dord.offlineattendance.data.local.entity.CandidateEntity
import com.dord.offlineattendance.domain.model.candidateMasterData.Wrapped

fun Wrapped.toCandidateEntity(): CandidateEntity {
    return CandidateEntity(
        candidateId = candidateId ?: "UNKNOWN_${batchId ?: 0}",
        batchId = batchId?.toLong() ?: 0L,
        candidateName = candidateName ?: "N/A",
        candidateEmail = emailId,
        rollNo = rollNo,
        mobileNo = mobileNo,
        gender = gender,
        dateOfBirth = dateOfBirth,
        address = address,
        aadhaarNo = aadhaarNo
    )
}