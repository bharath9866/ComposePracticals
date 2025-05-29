package com.adaptive.qrcodescanner.data.local.entity

@Entity(tableName = "scan_results")
data class ScanResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content: String,
    val type: String,
    val timestamp: String,
    val isValid: Boolean = true
)

fun ScanResultEntity.toDomainModel(): ScanResult {
    return ScanResult(
        id = id,
        content = content,
        type = ScanType.valueOf(type),
        timestamp = timestamp,
        isValid = isValid
    )
}

fun ScanResult.toEntity(): ScanResultEntity {
    return ScanResultEntity(
        id = id,
        content = content,
        type = type.name,
        timestamp = timestamp,
        isValid = isValid
    )
}