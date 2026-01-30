package com.example.attendance.data.mapper

import com.example.attendance.data.local.entity.CandidateEntity
import com.example.attendance.domain.model.candidateMasterData.Wrapped

fun Wrapped.toCandidateEntity(): CandidateEntity {
    return CandidateEntity(
        candidateId = candidateId,
        batchId = batchId.toLong(),
        candidateName = candidateName,
        rollNo = rollNo,
        mobileNo = mobileNo,
        gender = gender,
        dateOfBirth = dateOfBirth,
        address = address,
        aadhaarNo = aadhaarNo
    )
}