package com.dord.offlineattendance.di

import com.dord.offlineattendance.data.repository.AttendanceRepositoryImpl
import com.dord.offlineattendance.data.repository.BatchRepositoryImpl
import com.dord.offlineattendance.data.repository.CandidateRepositoryImpl
import com.dord.offlineattendance.data.repository.FacultyRepositoryImpl
import com.dord.offlineattendance.data.repository.SyncAttendanceRepositoryImpl
import com.dord.offlineattendance.domain.repository.AttendanceRepository
import com.dord.offlineattendance.domain.repository.BatchRepository
import com.dord.offlineattendance.domain.repository.CandidateRepository
import com.dord.offlineattendance.domain.repository.FacultyRepository
import com.dord.offlineattendance.domain.repository.SyncAttendanceRepository
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

    @Binds
    @Singleton
    abstract fun bindSyncAttendanceRepository(
        impl: SyncAttendanceRepositoryImpl
    ): SyncAttendanceRepository
}