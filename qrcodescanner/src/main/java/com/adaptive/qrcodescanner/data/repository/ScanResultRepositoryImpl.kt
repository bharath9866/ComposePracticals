package com.adaptive.qrcodescanner.data.repository

@Singleton
class ScanResultRepositoryImpl @Inject constructor(
    private val scanResultDao: ScanResultDao
) : ScanResultRepository {
    
    override fun getAllScanResults(): Flow<List<ScanResult>> {
        return scanResultDao.getAllScanResults().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override suspend fun getScanResultById(id: Long): ScanResult? {
        return scanResultDao.getScanResultById(id)?.toDomainModel()
    }
    
    override suspend fun insertScanResult(scanResult: ScanResult): Long {
        return scanResultDao.insertScanResult(scanResult.toEntity())
    }
    
    override suspend fun deleteScanResult(scanResult: ScanResult) {
        scanResultDao.deleteScanResult(scanResult.toEntity())
    }
    
    override suspend fun deleteScanResultById(id: Long) {
        scanResultDao.deleteScanResultById(id)
    }
    
    override suspend fun deleteAllScanResults() {
        scanResultDao.deleteAllScanResults()
    }
    
    override suspend fun getScanResultCount(): Int {
        return scanResultDao.getScanResultCount()
    }
}