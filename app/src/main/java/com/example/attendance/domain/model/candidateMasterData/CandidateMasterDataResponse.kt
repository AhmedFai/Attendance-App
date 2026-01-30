package com.example.attendance.domain.model.candidateMasterData

data class CandidateMasterDataResponse(
    val errorsMap: ErrorsMap,
    val facilityId: Int,
    val responseCode: Int,
    val responseDesc: String,
    val wrappedList: List<Wrapped>
)