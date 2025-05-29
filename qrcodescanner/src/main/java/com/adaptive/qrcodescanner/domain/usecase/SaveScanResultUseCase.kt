package com.adaptive.qrcodescanner.domain.usecase

class SaveScanResultUseCase @Inject constructor(
    private val repository: ScanResultRepository
) {
    suspend operator fun invoke(content: String): Long {
        val scanType = QRCodeAnalyzer.analyzeScanResult(content)
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        
        val scanResult = ScanResult(
            content = content,
            type = scanType,
            timestamp = timestamp,
            isValid = content.isNotBlank()
        )
        
        return repository.insertScanResult(scanResult)
    }
}