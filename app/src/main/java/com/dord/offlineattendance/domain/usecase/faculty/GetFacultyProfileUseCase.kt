package com.dord.offlineattendance.domain.usecase.faculty

import com.dord.offlineattendance.data.local.entity.FacultyEntity
import com.dord.offlineattendance.domain.repository.FacultyRepository
import javax.inject.Inject

class GetFacultyProfileUseCase @Inject constructor(
    private val repository: FacultyRepository
) {
    suspend operator fun invoke(): FacultyEntity? {
        return repository.getFacultyProfile()
    }
}