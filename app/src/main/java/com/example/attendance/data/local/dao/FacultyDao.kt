package com.example.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.attendance.data.local.entity.FacultyEntity

@Dao
interface FacultyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFaculties(faculties: List<FacultyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFaculty(faculty: FacultyEntity)

    @Query("""SELECT * FROM faculty LIMIT 1""")
    suspend fun getFacultyProfile(): FacultyEntity?

    @Query("""SELECT * FROM faculty WHERE batchId = :batchId LIMIT 1""")
    suspend fun getFacultyByBatch(batchId: Long): FacultyEntity?

    @Query("""SELECT DISTINCT batchId FROM faculty""")
    suspend fun getFacultyBatchIds(): List<Long>

    @Query("""SELECT EXISTS(SELECT 1 FROM faculty WHERE loginId = :loginId AND batchId = :batchId)""")
    suspend fun isFacultyAssignedToBatch(loginId: String, batchId: Long): Boolean

    @Query("DELETE FROM faculty")
    suspend fun clearFaculty()

}