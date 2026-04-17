package com.dord.offlineattendance.data.mapper

import com.dord.offlineattendance.data.local.entity.BatchEntity
import com.dord.offlineattendance.domain.model.candidateMasterData.Wrapped

fun Wrapped.toBatchEntity(): BatchEntity? {

    val safeBatchId = batchId ?: return null

    return BatchEntity(
        batchId = safeBatchId.toLong(),
        batchName = batchName.orEmpty(),
        batchRegNo = batchRegNo.orEmpty(),
        startDate = startDate.orEmpty(),
        endDate = endDate.orEmpty(),
        latitude = latitude?.toDoubleOrNull() ?: 0.0,
        longitude = longitude?.toDoubleOrNull() ?: 0.0,
        radius = radius ?: 0
    )
}