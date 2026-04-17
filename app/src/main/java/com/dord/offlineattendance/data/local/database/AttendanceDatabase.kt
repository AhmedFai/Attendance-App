package com.dord.offlineattendance.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dord.offlineattendance.data.local.dao.AttendanceDao
import com.dord.offlineattendance.data.local.dao.BatchDao
import com.dord.offlineattendance.data.local.dao.CandidateDao
import com.dord.offlineattendance.data.local.dao.FacultyDao
import com.dord.offlineattendance.data.local.entity.AttendanceEntity
import com.dord.offlineattendance.data.local.entity.BatchEntity
import com.dord.offlineattendance.data.local.entity.CandidateEntity
import com.dord.offlineattendance.data.local.entity.FacultyEntity

@Database(
    entities = [
        BatchEntity::class,
        CandidateEntity::class,
        FacultyEntity::class,
        AttendanceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AttendanceDatabase : RoomDatabase() {
    abstract fun batchDao(): BatchDao
    abstract fun candidateDao(): CandidateDao
    abstract fun facultyDao(): FacultyDao
    abstract fun attendanceDao(): AttendanceDao
}