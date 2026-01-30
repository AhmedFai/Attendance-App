package com.example.attendance.domain.usecase.faculty

import com.example.attendance.data.local.entity.FacultyEntity
import com.example.attendance.domain.repository.FacultyRepository
import javax.inject.Inject

class InsertFacultiesUseCase @Inject constructor(
    private val repository: FacultyRepository
) {
    suspend operator fun invoke(faculties: List<FacultyEntity>) {
        repository.insertFaculties(faculties)
    }
}