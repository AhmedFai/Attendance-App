package com.example.attendance.data.mapper

import com.example.attendance.data.local.entity.BatchEntity
import com.example.attendance.domain.model.candidateMasterData.Wrapped

fun Wrapped.toBatchEntity(): BatchEntity {
    return BatchEntity(
        batchId = batchId.toLong(),
        batchName = batchName,
        batchRegNo = batchRegNo,
        startDate = startDate,
        endDate = endDate,
        latitude = latitude.toDouble(),
        longitude = longitude.toDouble(),
        radius = radius

    )
}