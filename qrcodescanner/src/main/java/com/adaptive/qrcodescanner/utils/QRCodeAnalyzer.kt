package com.adaptive.qrcodescanner.utils

object QRCodeAnalyzer {
    
    private val URL_PATTERN = Pattern.compile(
        "^(https?://)?(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&=]*)\$"
    )
    
    private val EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}\$"
    )
    
    private val PHONE_PATTERN = Pattern.compile(
        "^[+]?[1-9]?[0-9]{7,15}\$"
    )
    
    fun analyzeScanResult(content: String): ScanType {
        return when {
            content.startsWith("http://") || content.startsWith("https://") -> ScanType.URL
            content.startsWith("mailto:") -> ScanType.EMAIL
            content.startsWith("tel:") -> ScanType.PHONE
            content.startsWith("sms:") || content.startsWith("SMSTO:") -> ScanType.SMS
            content.startsWith("WIFI:") -> ScanType.WIFI
            content.startsWith("geo:") -> ScanType.GEO
            content.startsWith("BEGIN:VCARD") -> ScanType.CONTACT
            content.startsWith("BEGIN:VEVENT") -> ScanType.CALENDAR
            URL_PATTERN.matcher(content).matches() -> ScanType.URL
            EMAIL_PATTERN.matcher(content).matches() -> ScanType.EMAIL
            PHONE_PATTERN.matcher(content).matches() -> ScanType.PHONE
            else -> ScanType.TEXT
        }
    }
    
    fun getTypeIcon(scanType: ScanType): String {
        return when (scanType) {
            ScanType.URL -> "🌐"
            ScanType.EMAIL -> "📧"
            ScanType.PHONE -> "📞"
            ScanType.SMS -> "💬"
            ScanType.WIFI -> "📶"
            ScanType.GEO -> "📍"
            ScanType.CONTACT -> "👤"
            ScanType.CALENDAR -> "📅"
            ScanType.TEXT -> "📝"
            ScanType.UNKNOWN -> "❓"
        }
    }
    
    fun getTypeDescription(scanType: ScanType): String {
        return when (scanType) {
            ScanType.URL -> "Website URL"
            ScanType.EMAIL -> "Email Address"
            ScanType.PHONE -> "Phone Number"
            ScanType.SMS -> "SMS Message"
            ScanType.WIFI -> "WiFi Network"
            ScanType.GEO -> "Location"
            ScanType.CONTACT -> "Contact Card"
            ScanType.CALENDAR -> "Calendar Event"
            ScanType.TEXT -> "Plain Text"
            ScanType.UNKNOWN -> "Unknown Type"
        }
    }
}