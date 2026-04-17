package com.dord.offlineattendance.domain.model.facultyMasterData

data class FacultyMasterDataResponse(
    val errorsMap: ErrorsMap,
    val facilityId: Int,
    val responseCode: Int,
    val responseDesc: String,
    val wrappedList: List<Wrapped>
)