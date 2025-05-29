package com.adaptive.qrcodescanner.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun HistoryScreen(
    scanResults: List<ScanResult>,
    onNavigateBack: () -> Unit,
    onScanResultClick: (ScanResult) -> Unit,
    onDeleteScanResult: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Text(
                text = "Scan History",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        if (scanResults.isEmpty()) {
            // Empty state
            EmptyHistoryState(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        } else {
            // Scan results list
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = scanResults,
                    key = { it.id }
                ) { scanResult ->
                    ScanResultItem(
                        scanResult = scanResult,
                        onClick = { onScanResultClick(scanResult) },
                        onDelete = { onDeleteScanResult(scanResult.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyHistoryState(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterVertically,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "📱",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text(
                text = "No Scans Yet",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "Start scanning QR codes to see your history here",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScanResultItem(
    scanResult: ScanResult,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Type icon
            Card(
                modifier = Modifier.size(48.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = CircleShape
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = QRCodeAnalyzer.getTypeIcon(scanResult.type),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            
            // Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = QRCodeAnalyzer.getTypeDescription(scanResult.type),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = scanResult.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
                
                Text(
                    text = formatTimestamp(scanResult.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            // Delete button
            IconButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
    
    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text("Delete Scan Result")
            },
            text = {
                Text("Are you sure you want to delete this scan result?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun formatTimestamp(timestamp: String): String {
    return try {
        val localDateTime = LocalDateTime.parse(timestamp)
        localDateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy • HH:mm"))
    } catch (e: Exception) {
        timestamp
    }
}
