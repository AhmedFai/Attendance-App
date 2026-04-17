package com.dord.offlineattendance.domain.usecase.faculty

import com.dord.offlineattendance.domain.repository.FacultyRepository
import javax.inject.Inject

class ClearFacultyUseCase @Inject constructor(
    private val repository: FacultyRepository
) {
    suspend operator fun invoke() {
        repository.clearFaculty()
    }
}