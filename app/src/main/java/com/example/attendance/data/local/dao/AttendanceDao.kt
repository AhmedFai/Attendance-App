package com.example.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.attendance.data.local.entity.AttendanceEntity

@Dao
interface AttendanceDao {

    // insert check-in
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity)

    // Check-in ho chuka hai ya nahi (aaj)
    @Query("""SELECT EXISTS(SELECT 1 FROM attendance WHERE userId = :userId AND userType = :userType AND batchId = :batchId AND attendanceDate = :date AND checkIn IS NOT NULL)""")
    suspend fun isCheckedInToday(userId: String, userType: String, batchId: Long, date: String): Boolean

    // Check-out ho chuka hai ya nahi (aaj)
    @Query("""SELECT EXISTS(SELECT 1 FROM attendance WHERE userId = :userId AND userType = :userType AND batchId = :batchId AND attendanceDate = :date AND checkOut IS NOT NULL)""")
    suspend fun isCheckedOutToday(userId: String, userType: String, batchId: Long, date: String): Boolean

    // get check-in time
    @Query("""SELECT checkIn FROM attendance WHERE userId = :userId AND userType = :userType AND batchId = :batchId AND attendanceDate = :date LIMIT 1""")
    suspend fun getCheckInTime(userId: String, userType: String, batchId: Long, date: String): String?

    // get check-out time
    @Query("""SELECT checkOut FROM attendance WHERE userId = :userId AND userType = :userType AND batchId = :batchId AND attendanceDate = :date LIMIT 1""")
    suspend fun getCheckOutTime(userId: String, userType: String, batchId: Long, date: String): String?

    // check-out update karne ke liye
    @Query("""
        UPDATE attendance
        SET checkOut = :checkOut,
            totalHours = :totalHours,
            syncStatus = :syncStatus
        WHERE userId = :userId
        AND userType = :userType
        AND batchId = :batchId
        AND attendanceDate = :date
    """)
    suspend fun updateCheckOut(
        userId: String,
        userType: String,
        batchId: Long,
        date: String,
        checkOut: String,
        totalHours: String,
        syncStatus: String
    )

    // SYNC RELATED

    // Unsynced attendance (Candidate + Faculty both)
    @Query("""SELECT * FROM attendance WHERE syncStatus = 'PENDING'""")
    suspend fun getPendingAttendances(): List<AttendanceEntity>

    // Mark synced (after API success)
    @Query("""UPDATE attendance SET syncStatus = 'SYNCED' WHERE id IN (:attendanceIds)""")
    suspend fun markAttendancesSynced(attendanceIds: List<Long>)


    // COUNTS (Home / Dashboard)

    @Query("""SELECT COUNT(*) FROM attendance WHERE syncStatus = 'PENDING'""")
    suspend fun getPendingCount(): Int

    @Query("""SELECT COUNT(*) FROM attendance WHERE syncStatus = 'SYNCED'""")
    suspend fun getSyncedCount(): Int


    @Query("DELETE FROM attendance")
    suspend fun clearAttendance()
}