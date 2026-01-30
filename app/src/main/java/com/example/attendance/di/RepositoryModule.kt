package com.example.attendance.di

import com.example.attendance.data.repository.AttendanceRepositoryImpl
import com.example.attendance.data.repository.BatchRepositoryImpl
import com.example.attendance.data.repository.CandidateRepositoryImpl
import com.example.attendance.data.repository.FacultyRepositoryImpl
import com.example.attendance.domain.repository.AttendanceRepository
import com.example.attendance.domain.repository.BatchRepository
import com.example.attendance.domain.repository.CandidateRepository
import com.example.attendance.domain.repository.FacultyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBatchRepository(
        impl: BatchRepositoryImpl
    ): BatchRepository

    @Binds
    @Singleton
    abstract fun bindCandidateRepository(
        impl: CandidateRepositoryImpl
    ): CandidateRepository

    @Binds
    @Singleton
    abstract fun bindFacultyRepository(
        impl: FacultyRepositoryImpl
    ): FacultyRepository

    @Binds
    @Singleton
    abstract fun bindAttendanceRepository(
        impl: AttendanceRepositoryImpl
    ): AttendanceRepository

}