package com.github.contactmanager.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Utility class for handling phone call operations
 */
object PhoneCallUtil {

    /**
     * Opens the dialer with a pre-filled phone number.
     * This method doesn't require special permissions.
     *
     * @param context The context to use for opening the dialer
     * @param phoneNumber The phone number to fill in the dialer
     */
    fun openDialer(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    /**
     * Initiates a phone call to the specified number.
     * Requires CALL_PHONE permission.
     *
     * @param context The context to use for initiating the call
     * @param phoneNumber The phone number to call
     * @return true if the call was initiated, false if permission is not granted
     */
    fun initiatePhoneCall(context: Context, phoneNumber: String): Boolean {
        if (hasCallPhonePermission(context)) {
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$phoneNumber")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            return true
        }
        return false
    }

    /**
     * Checks if the CALL_PHONE permission is granted.
     *
     * @param context The context to check for permissions
     * @return true if permission is granted, false otherwise
     */
    fun hasCallPhonePermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Formats a phone number for better display.
     * Simple formatting for demo purposes.
     *
     * @param phoneNumber The raw phone number
     * @return Formatted phone number
     */
    fun formatPhoneNumber(phoneNumber: String): String {
        // This is a very simple formatter
        // In a real app, you might use libphonenumber
        if (phoneNumber.length == 10) {
            return "(${phoneNumber.substring(0, 3)}) ${phoneNumber.substring(3, 6)}-${phoneNumber.substring(6)}"
        }
        return phoneNumber
    }
}