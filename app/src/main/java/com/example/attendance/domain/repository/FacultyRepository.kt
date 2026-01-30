package com.example.attendance.domain.repository

import com.example.attendance.data.local.entity.FacultyEntity

interface FacultyRepository {

    suspend fun insertFaculties(faculties: List<FacultyEntity>)

    suspend fun insertFaculty(faculty: FacultyEntity)

    suspend fun getFacultyProfile(): FacultyEntity?

    suspend fun getFacultyByBatch(batchId: Long): FacultyEntity?

    suspend fun getFacultyBatchIds(): List<Long>

    suspend fun isFacultyAssignedToBatch(
        loginId: String,
        batchId: Long
    ): Boolean

    suspend fun clearFaculty()

}