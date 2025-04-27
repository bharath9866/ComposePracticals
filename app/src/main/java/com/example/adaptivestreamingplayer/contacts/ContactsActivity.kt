package com.example.adaptivestreamingplayer.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.github.contactmanager.ui.ContactManagerApp

class ContactsActivity : ComponentActivity() {
    
    // Permission request launcher for contacts
    private val requestContactsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Contacts permission granted", Toast.LENGTH_SHORT).show()
            // Refresh the UI to load contacts
            setContent {
                ContactManagerApp()
            }
        } else {
            Toast.makeText(this, "Contacts permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Request contacts permission if needed
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            requestContactsPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        } else {
            // Permission already granted, show the contact manager UI
            setContent {
                ContactManagerApp()
            }
        }
    }
}