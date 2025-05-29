package com.adaptive.qrcodescanner.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun ResultScreen(
    scanResult: ScanResult,
    onNavigateBack: () -> Unit,
    onScanAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var showCopySnackbar by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
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
                text = "Scan Result",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // Type and icon card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = QRCodeAnalyzer.getTypeIcon(scanResult.type),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = QRCodeAnalyzer.getTypeDescription(scanResult.type),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = formatTimestamp(scanResult.timestamp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Content card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Content",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                SelectionContainer {
                    Text(
                        text = scanResult.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )
                }
            }
        }

        // Action buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Copy button
            OutlinedButton(
                onClick = {
                    copyToClipboard(context, scanResult.content)
                    showCopySnackbar = true
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Copy")
            }
            
            // Share button
            OutlinedButton(
                onClick = {
                    shareContent(context, scanResult.content)
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share")
            }
        }

        // Conditional action button based on type
        when (scanResult.type) {
            ScanType.URL -> {
                Button(
                    onClick = { openUrl(context, scanResult.content) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Launch,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Open Website")
                }
            }
            ScanType.EMAIL -> {
                Button(
                    onClick = { sendEmail(context, scanResult.content) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Launch,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Send Email")
                }
            }
            ScanType.PHONE -> {
                Button(
                    onClick = { makePhoneCall(context, scanResult.content) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Launch,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Call Number")
                }
            }
            else -> {
                // No special action for other types
            }
        }

        // Scan again button
        Button(
            onClick = onScanAgain,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.QrCodeScanner,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Scan Again")
        }
    }

    // Snackbar for copy confirmation
    if (showCopySnackbar) {
        LaunchedEffect(showCopySnackbar) {
            kotlinx.coroutines.delay(2000)
            showCopySnackbar = false
        }
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("QR Code Content", text)
    clipboard.setPrimaryClip(clip)
}

private fun shareContent(context: Context, content: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share QR Code Content"))
}

private fun openUrl(context: Context, url: String) {
    try {
        val formattedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
            "https://$url"
        } else {
            url
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl))
        context.startActivity(intent)
    } catch (e: Exception) {
        // Handle error
    }
}

private fun sendEmail(context: Context, email: String) {
    try {
        val emailAddress = if (email.startsWith("mailto:")) {
            email.substring(7)
        } else {
            email
        }
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$emailAddress")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        // Handle error
    }
}

private fun makePhoneCall(context: Context, phoneNumber: String) {
    try {
        val number = if (phoneNumber.startsWith("tel:")) {
            phoneNumber.substring(4)
        } else {
            phoneNumber
        }
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        context.startActivity(intent)
    } catch (e: Exception) {
        // Handle error
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