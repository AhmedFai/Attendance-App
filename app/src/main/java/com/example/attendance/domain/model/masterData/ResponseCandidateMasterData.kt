package com.example.attendance.domain.model.masterData

data class ResponseCandidateMasterData(
    val responseCode: Int,
    val responseDesc: String,
    val wrappedList: List<Wrapped>
)