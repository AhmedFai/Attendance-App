package com.example.attendance.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "candidate")
data class CandidateEntity(
    @PrimaryKey val candidateId: String,
    val batchId: Long,
    val candidateName: String,
    val candidateEmail: String?,
    val rollNo: Int,
    val mobileNo: String?,
    val gender: String?,
    val dateOfBirth: String?,
    val address: String?,
    val aadhaarNo: String?
)
