package com.example.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.attendance.data.local.entity.CandidateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CandidateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCandidate(list: List<CandidateEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCandidate(candidate: CandidateEntity)

    @Query("SELECT * FROM candidate")
    suspend fun getAllCandidate() : List<CandidateEntity>

    @Query("SELECT * FROM candidate WHERE batchId = :batchId ORDER BY rollNo ASC")
    fun getCandidateByBatch(batchId: Long) : Flow<List<CandidateEntity>>

    @Query("SELECT COUNT(*) FROM candidate")
    suspend fun getCandidateCount(): Int

    @Query("""SELECT EXISTS(SELECT 1 FROM candidate WHERE candidateId = :candidateId)""")
    suspend fun isCandidateExists(candidateId: String): Boolean

    @Query("""SELECT * FROM candidate WHERE candidateId = :candidateId LIMIT 1""")
    suspend fun getCandidateById(candidateId: String): CandidateEntity?

    @Query("DELETE FROM candidate")
    suspend fun clearCandidates()

}