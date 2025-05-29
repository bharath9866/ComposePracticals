package com.adaptive.qrcodescanner.presentation.screens

data class MainUiState(
    val isLoading: Boolean = false,
    val scanResults: List<ScanResult> = emptyList(),
    val currentScanResult: ScanResult? = null,
    val error: String? = null,
    val isScanning: Boolean = false,
    val hasCameraPermission: Boolean = false,
    val showPermissionDialog: Boolean = false,
    val currentScreen: Screen = Screen.Scanner
)

enum class Screen {
    Scanner,
    History,
    Result
}

sealed class MainUiEvent {
    object StartScanning : MainUiEvent()
    object StopScanning : MainUiEvent()
    data class OnQRCodeScanned(val content: String) : MainUiEvent()
    object NavigateToHistory : MainUiEvent()
    object NavigateToScanner : MainUiEvent()
    data class NavigateToResult(val scanResult: ScanResult) : MainUiEvent()
    data class DeleteScanResult(val id: Long) : MainUiEvent()
    object ClearError : MainUiEvent()
    object RequestCameraPermission : MainUiEvent()
    object PermissionGranted : MainUiEvent()
    object PermissionDenied : MainUiEvent()
    object DismissPermissionDialog : MainUiEvent()
    object ClearHistory : MainUiEvent()
}