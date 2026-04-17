package com.dord.offlineattendance.data.mapper

import com.dord.offlineattendance.data.local.entity.FacultyEntity
import com.dord.offlineattendance.domain.model.facultyMasterData.Wrapped

//fun Wrapped.toBatchEntity(): FacultyEntity {
//    return FacultyEntity(
//        loginId = loginId,
//        facultyName = facultyName,
//        emailId = emailId,
//        mobileNo = mobileNo,
//        gender = gender,
//        dob = dob,
//        batchId = batchId.toLong(),
//        batchRegNo = batchRegNo,
//        startDate = startDate,
//        endDate = endDate
//    )
//}

fun Wrapped.toBatchEntity(): FacultyEntity? {

    val safeLoginId = loginId ?: return null
    val safeBatchId = batchId ?: return null

    return FacultyEntity(
        loginId = safeLoginId,
        facultyName = facultyName.orEmpty(),
        emailId = emailId,
        mobileNo = mobileNo,
        gender = gender,
        dob = dob,
        batchId = safeBatchId.toLong(),
        batchRegNo = batchRegNo.orEmpty(),
        startDate = startDate.orEmpty(),
        endDate = endDate.orEmpty()
    )
}