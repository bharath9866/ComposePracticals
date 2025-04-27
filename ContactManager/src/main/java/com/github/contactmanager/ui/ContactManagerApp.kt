package com.github.contactmanager.ui

import android.Manifest
import android.app.Application
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.github.contactmanager.ui.navigation.ContactNavigation
import com.github.contactmanager.viewmodel.ContactViewModel

/**
 * Main entry point for the Contact Manager module.
 * 
 * This app follows MVVM architecture and SOLID principles:
 * - Model: Contact data class represents the data model
 * - View: Composable UI components in this file and the screens package
 * - ViewModel: ContactViewModel provides UI state and business logic
 * 
 * SOLID Principles:
 * - Single Responsibility: Each class has one responsibility
 * - Open/Closed: Classes are open for extension but closed for modification
 * - Liskov Substitution: Components can be replaced with derived classes
 * - Interface Segregation: Small, specific interfaces (like permission checking)
 * - Dependency Inversion: High-level modules depend on abstractions (Repository pattern)
 */
@Composable
fun ContactManagerApp() {
    val context = LocalContext.current
    val viewModel = remember { ContactViewModel(context.applicationContext as Application) }
    
    // Permission handling for both read and write permissions
    var hasReadContactsPermission by remember { mutableStateOf(false) }
    var hasWriteContactsPermission by remember { mutableStateOf(false) }
    
    // Request write permission launcher
    val writePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasWriteContactsPermission = isGranted
        if (isGranted) {
            viewModel.onWritePermissionGranted()
        }
    }
    
    // Request read permission launcher
    val readPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasReadContactsPermission = isGranted
        // If read permission is granted, also request write permission
        if (isGranted) {
            writePermissionLauncher.launch(Manifest.permission.WRITE_CONTACTS)
        }
    }
    
    // Request permissions when the app starts
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                // First request READ_CONTACTS permission
                readPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    
    Surface(color = MaterialTheme.colorScheme.background) {
        ContactNavigation(viewModel = viewModel)
    }
}