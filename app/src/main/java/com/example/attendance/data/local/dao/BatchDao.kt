package com.example.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.attendance.data.local.entity.BatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatches(batches : List<BatchEntity>)

    @Query("""SELECT * FROM batch WHERE DATE('now') BETWEEN DATE(startDate) AND DATE(endDate)""")
    fun getActiveBatches() : Flow<List<BatchEntity>>

    @Query("""SELECT * FROM batch WHERE batchId = :batchId LIMIT 1""")
    suspend fun getBatchById(batchId: Long) : BatchEntity?

    @Query("DELETE FROM batch")
    suspend fun clearBatch()

}