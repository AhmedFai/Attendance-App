package com.example.attendance.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.attendance.data.local.dao.AttendanceDao
import com.example.attendance.data.local.dao.BatchDao
import com.example.attendance.data.local.dao.CandidateDao
import com.example.attendance.data.local.dao.FacultyDao
import com.example.attendance.data.local.entity.AttendanceEntity
import com.example.attendance.data.local.entity.BatchEntity
import com.example.attendance.data.local.entity.CandidateEntity
import com.example.attendance.data.local.entity.FacultyEntity

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
 abstract class AttendanceDatabase : RoomDatabase(){
     abstract fun batchDao(): BatchDao
     abstract fun candidateDao(): CandidateDao
     abstract fun facultyDao(): FacultyDao
     abstract fun attendanceDao(): AttendanceDao
}