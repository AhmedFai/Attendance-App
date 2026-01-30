package com.example.attendance.data.mapper

import com.example.attendance.data.local.entity.FacultyEntity
import com.example.attendance.domain.model.facultyMasterData.Wrapped

fun Wrapped.toBatchEntity(): FacultyEntity {
    return FacultyEntity(
        loginId = loginId,
        facultyName = facultyName,
        emailId = emailId,
        mobileNo = mobileNo,
        gender = gender,
        dob = dob,
        batchId = batchId.toLong(),
        batchRegNo = batchRegNo,
        startDate = startDate,
        endDate = endDate
    )
}