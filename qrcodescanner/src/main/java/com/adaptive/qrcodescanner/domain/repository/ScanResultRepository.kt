package com.adaptive.qrcodescanner.domain.repository

interface ScanResultRepository {
    
    fun getAllScanResults(): Flow<List<ScanResult>>
    
    suspend fun getScanResultById(id: Long): ScanResult?
    
    suspend fun insertScanResult(scanResult: ScanResult): Long
    
    suspend fun deleteScanResult(scanResult: ScanResult)
    
    suspend fun deleteScanResultById(id: Long)
    
    suspend fun deleteAllScanResults()
    
    suspend fun getScanResultCount(): Int
}