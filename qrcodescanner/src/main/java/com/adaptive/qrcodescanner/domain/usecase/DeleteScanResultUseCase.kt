package com.adaptive.qrcodescanner.domain.usecase

class DeleteScanResultUseCase @Inject constructor(
    private val repository: ScanResultRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteScanResultById(id)
    }
}