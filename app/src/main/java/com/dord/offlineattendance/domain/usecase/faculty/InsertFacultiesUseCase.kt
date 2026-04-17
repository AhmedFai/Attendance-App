package com.dord.offlineattendance.domain.usecase.faculty

import com.dord.offlineattendance.data.local.entity.FacultyEntity
import com.dord.offlineattendance.domain.repository.FacultyRepository
import javax.inject.Inject

class InsertFacultiesUseCase @Inject constructor(
    private val repository: FacultyRepository
) {
    suspend operator fun invoke(faculties: List<FacultyEntity>) {
        repository.insertFaculties(faculties)
    }
}