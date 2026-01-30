package com.example.attendance.data.repository

import com.example.attendance.data.local.dao.FacultyDao
import com.example.attendance.data.local.entity.FacultyEntity
import com.example.attendance.domain.repository.FacultyRepository
import javax.inject.Inject

class FacultyRepositoryImpl @Inject constructor(
    private val facultyDao: FacultyDao
) : FacultyRepository {
    override suspend fun insertFaculties(faculties: List<FacultyEntity>) {
        facultyDao.insertFaculties(faculties)
    }

    override suspend fun insertFaculty(faculty: FacultyEntity) {
        facultyDao.insertFaculty(faculty)
    }

    override suspend fun getFacultyProfile(): FacultyEntity? {
        return facultyDao.getFacultyProfile()
    }

    override suspend fun getFacultyByBatch(batchId: Long): FacultyEntity? {
        return facultyDao.getFacultyByBatch(batchId)
    }

    override suspend fun getFacultyBatchIds(): List<Long> {
        return facultyDao.getFacultyBatchIds()
    }

    override suspend fun isFacultyAssignedToBatch(
        loginId: String,
        batchId: Long
    ): Boolean {
        return facultyDao.isFacultyAssignedToBatch(loginId, batchId)
    }

    override suspend fun clearFaculty() {
        facultyDao.clearFaculty()
    }
}