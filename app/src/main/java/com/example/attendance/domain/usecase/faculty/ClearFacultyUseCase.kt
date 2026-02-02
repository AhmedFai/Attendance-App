package com.example.attendance.domain.usecase.faculty

import com.example.attendance.domain.repository.FacultyRepository
import javax.inject.Inject

class ClearFacultyUseCase @Inject constructor(
    private val repository: FacultyRepository
) {
    suspend operator fun invoke() {
        repository.clearFaculty()
    }
}