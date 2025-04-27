package com.github.contactmanager.service

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat

/**
 * Service to handle phone call operations.
 */
class PhoneCallService {

    /**
     * Initiates a phone call to the specified number.
     * 
     * @param context The context to use for initiating the call.
     * @param phoneNumber The phone number to call.
     * @return True if the call was initiated, false otherwise.
     */
    fun initiatePhoneCall(context: Context, phoneNumber: String): Boolean {
        // Check if the CALL_PHONE permission is granted
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Create the call intent
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$phoneNumber")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            
            // Start the call activity
            context.startActivity(intent)
            return true
        }
        return false
    }

    /**
     * Opens the dialer with the specified number pre-filled.
     * This method doesn't require special permissions.
     * 
     * @param context The context to use for opening the dialer.
     * @param phoneNumber The phone number to pre-fill.
     */
    fun openDialer(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}