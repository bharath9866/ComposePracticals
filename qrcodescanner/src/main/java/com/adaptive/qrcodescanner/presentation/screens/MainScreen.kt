package com.adaptive.qrcodescanner.presentation.screens

import androidx.compose.runtime.*

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (uiState.currentScreen) {
            Screen.Scanner -> {
                ScannerScreen(
                    onQRCodeScanned = { content ->
                        viewModel.handleEvent(MainUiEvent.OnQRCodeScanned(content))
                    },
                    onNavigateToHistory = {
                        viewModel.handleEvent(MainUiEvent.NavigateToHistory)
                    }
                )
            }
            
            Screen.History -> {
                HistoryScreen(
                    scanResults = uiState.scanResults,
                    onNavigateBack = {
                        viewModel.handleEvent(MainUiEvent.NavigateToScanner)
                    },
                    onScanResultClick = { scanResult ->
                        viewModel.handleEvent(MainUiEvent.NavigateToResult(scanResult))
                    },
                    onDeleteScanResult = { id ->
                        viewModel.handleEvent(MainUiEvent.DeleteScanResult(id))
                    }
                )
            }
            
            Screen.Result -> {
                uiState.currentScanResult?.let { scanResult ->
                    ResultScreen(
                        scanResult = scanResult,
                        onNavigateBack = {
                            viewModel.handleEvent(MainUiEvent.NavigateToScanner)
                        },
                        onScanAgain = {
                            viewModel.handleEvent(MainUiEvent.NavigateToScanner)
                        }
                    )
                }
            }
        }
    }
}
