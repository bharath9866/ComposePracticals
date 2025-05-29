package com.adaptive.qrcodescanner.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun QRCodeScanner(
    onQRCodeScanned: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor = remember { Executors.newSingleThreadExecutor() }
    
    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val cameraProvider = cameraProviderFuture.get()
                
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                
                val imageCapture = ImageCapture.Builder().build()
                
                val imageAnalyzer = ImageAnalysis.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(executor, QRCodeAnalyzer { barcode ->
                            barcode?.let { code ->
                                onQRCodeScanned(code)
                            }
                        })
                    }
                
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture,
                        imageAnalyzer
                    )
                } catch (exc: Exception) {
                    Timber.e(exc, "Use case binding failed")
                }
                
                previewView
            },
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )
        
        // Overlay for scanning frame
        ScanningOverlay(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun ScanningOverlay(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.size(250.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Point camera at QR code",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

private class QRCodeAnalyzer(
    private val onQRCodeScanned: (String?) -> Unit
) : ImageAnalysis.Analyzer {
    
    private val scanner = BarcodeScanning.getClient()
    
    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        when (barcode.valueType) {
                            Barcode.TYPE_TEXT,
                            Barcode.TYPE_URL,
                            Barcode.TYPE_EMAIL,
                            Barcode.TYPE_PHONE,
                            Barcode.TYPE_SMS,
                            Barcode.TYPE_WIFI,
                            Barcode.TYPE_GEO,
                            Barcode.TYPE_CONTACT_INFO,
                            Barcode.TYPE_CALENDAR_EVENT -> {
                                barcode.rawValue?.let { value ->
                                    onQRCodeScanned(value)
                                }
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.e(exception, "Barcode scanning failed")
                    onQRCodeScanned(null)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}