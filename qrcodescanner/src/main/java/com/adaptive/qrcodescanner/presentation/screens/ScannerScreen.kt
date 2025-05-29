package com.adaptive.qrcodescanner.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen(
    onQRCodeScanned: (String) -> Unit,
    onNavigateToHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cameraPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.CAMERA
    )

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "QR Scanner",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            IconButton(
                onClick = onNavigateToHistory,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "History",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        when {
            cameraPermissionState.status.isGranted -> {
                // Camera permission granted
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    QRCodeScanner(
                        onQRCodeScanned = onQRCodeScanned,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                
                // Instructions
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "Point your camera at a QR code to scan it automatically",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            cameraPermissionState.status.shouldShowRationale -> {
                // Permission denied but can be requested again
                PermissionRationaleCard(
                    onRequestPermission = { 
                        cameraPermissionState.launchPermissionRequest() 
                    }
                )
            }
            
            else -> {
                // Permission denied permanently
                PermissionDeniedCard()
            }
        }
    }
}

@Composable
private fun PermissionRationaleCard(
    onRequestPermission: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📷",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text(
                text = "Camera Permission Required",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "To scan QR codes, we need access to your camera. This allows the app to detect and read QR codes from your camera view.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            Button(
                onClick = onRequestPermission,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Grant Camera Permission")
            }
        }
    }
}

@Composable
private fun PermissionDeniedCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "❌",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text(
                text = "Camera Permission Denied",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "Camera permission is required to scan QR codes. Please enable it in your device settings to use this feature.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}
