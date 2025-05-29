package com.adaptive.qrcodescanner.domain.model

@Serializable
data class ScanResult(
    val id: Long = 0,
    val content: String,
    val type: ScanType,
    val timestamp: String, // Using String for serialization compatibility
    val isValid: Boolean = true
)

enum class ScanType {
    TEXT,
    URL,
    EMAIL,
    PHONE,
    SMS,
    WIFI,
    GEO,
    CONTACT,
    CALENDAR,
    UNKNOWN
}