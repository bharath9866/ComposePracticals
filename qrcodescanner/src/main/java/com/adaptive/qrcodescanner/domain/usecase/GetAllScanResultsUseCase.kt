package com.adaptive.qrcodescanner.domain.usecase

class GetAllScanResultsUseCase @Inject constructor(
    private val repository: ScanResultRepository
) {
    operator fun invoke(): Flow<List<ScanResult>> {
        return repository.getAllScanResults()
    }
}