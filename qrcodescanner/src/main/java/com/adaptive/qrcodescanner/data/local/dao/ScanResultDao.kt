package com.adaptive.qrcodescanner.data.local.dao

import androidx.room.*

@Dao
interface ScanResultDao {
    
    @Query("SELECT * FROM scan_results ORDER BY timestamp DESC")
    fun getAllScanResults(): Flow<List<ScanResultEntity>>
    
    @Query("SELECT * FROM scan_results WHERE id = :id")
    suspend fun getScanResultById(id: Long): ScanResultEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScanResult(scanResult: ScanResultEntity): Long
    
    @Delete
    suspend fun deleteScanResult(scanResult: ScanResultEntity)
    
    @Query("DELETE FROM scan_results WHERE id = :id")
    suspend fun deleteScanResultById(id: Long)
    
    @Query("DELETE FROM scan_results")
    suspend fun deleteAllScanResults()
    
    @Query("SELECT COUNT(*) FROM scan_results")
    suspend fun getScanResultCount(): Int
}
