package com.example.attendance.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "batch")
data class BatchEntity(
    @PrimaryKey val batchId: Long,
    val batchName: String,
    val batchRegNo: String,
    val startDate: String,
    val endDate: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Int
)
