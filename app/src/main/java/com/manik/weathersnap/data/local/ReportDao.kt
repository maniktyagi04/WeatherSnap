package com.manik.weathersnap.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for weather reports.
 * Manages active reports and a soft-delete trash system.
 */
@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ReportEntity)

    @Update
    suspend fun updateReport(report: ReportEntity)

    // Only get reports that are NOT deleted
    @Query("SELECT * FROM reports WHERE isDeleted = 0 ORDER BY timestamp DESC")
    fun getActiveReports(): Flow<List<ReportEntity>>

    // Get only deleted reports for Trash Screen
    @Query("SELECT * FROM reports WHERE isDeleted = 1 ORDER BY deletedAt DESC")
    fun getTrashReports(): Flow<List<ReportEntity>>

    @Query("SELECT * FROM reports WHERE id = :id")
    suspend fun getReportById(id: Int): ReportEntity?

    @Query("UPDATE reports SET isDeleted = 1, deletedAt = :timestamp WHERE id = :id")
    suspend fun softDeleteReport(id: Int, timestamp: Long)

    @Query("UPDATE reports SET isDeleted = 0, deletedAt = null WHERE id = :id")
    suspend fun restoreReport(id: Int)

    @Delete
    suspend fun permanentlyDeleteReport(report: ReportEntity)
}
