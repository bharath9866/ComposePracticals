package com.adaptive.qrcodescanner.presentation.viewmodel

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllScanResultsUseCase: GetAllScanResultsUseCase,
    private val saveScanResultUseCase: SaveScanResultUseCase,
    private val deleteScanResultUseCase: DeleteScanResultUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadScanResults()
    }

    fun handleEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.StartScanning -> startScanning()
            is MainUiEvent.StopScanning -> stopScanning()
            is MainUiEvent.OnQRCodeScanned -> onQRCodeScanned(event.content)
            is MainUiEvent.NavigateToHistory -> navigateToHistory()
            is MainUiEvent.NavigateToScanner -> navigateToScanner()
            is MainUiEvent.NavigateToResult -> navigateToResult(event.scanResult)
            is MainUiEvent.DeleteScanResult -> deleteScanResult(event.id)
            is MainUiEvent.ClearError -> clearError()
            is MainUiEvent.RequestCameraPermission -> requestCameraPermission()
            is MainUiEvent.PermissionGranted -> onPermissionGranted()
            is MainUiEvent.PermissionDenied -> onPermissionDenied()
            is MainUiEvent.DismissPermissionDialog -> dismissPermissionDialog()
            is MainUiEvent.ClearHistory -> clearHistory()
        }
    }

    private fun loadScanResults() {
        viewModelScope.launch {
            getAllScanResultsUseCase()
                .catch { error ->
                    Timber.e(error, "Error loading scan results")
                    _uiState.value = _uiState.value.copy(
                        error = "Failed to load scan history",
                        isLoading = false
                    )
                }
                .collect { scanResults ->
                    _uiState.value = _uiState.value.copy(
                        scanResults = scanResults,
                        isLoading = false
                    )
                }
        }
    }

    private fun startScanning() {
        _uiState.value = _uiState.value.copy(isScanning = true)
    }

    private fun stopScanning() {
        _uiState.value = _uiState.value.copy(isScanning = false)
    }

    private fun onQRCodeScanned(content: String) {
        viewModelScope.launch {
            try {
                val id = saveScanResultUseCase(content)
                Timber.d("QR Code saved with ID: $id, Content: $content")
                
                // Find the newly saved scan result and navigate to result screen
                val scanResults = _uiState.value.scanResults
                val newScanResult = scanResults.find { it.content == content }
                newScanResult?.let { result ->
                    _uiState.value = _uiState.value.copy(
                        currentScanResult = result,
                        currentScreen = Screen.Result,
                        isScanning = false
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "Error saving scan result")
                _uiState.value = _uiState.value.copy(
                    error = "Failed to save scan result",
                    isScanning = false
                )
            }
        }
    }

    private fun navigateToHistory() {
        _uiState.value = _uiState.value.copy(currentScreen = Screen.History)
    }

    private fun navigateToScanner() {
        _uiState.value = _uiState.value.copy(currentScreen = Screen.Scanner)
    }

    private fun navigateToResult(scanResult: com.adaptive.qrcodescanner.domain.model.ScanResult) {
        _uiState.value = _uiState.value.copy(
            currentScanResult = scanResult,
            currentScreen = Screen.Result
        )
    }

    private fun deleteScanResult(id: Long) {
        viewModelScope.launch {
            try {
                deleteScanResultUseCase(id)
                Timber.d("Scan result deleted: $id")
            } catch (e: Exception) {
                Timber.e(e, "Error deleting scan result")
                _uiState.value = _uiState.value.copy(
                    error = "Failed to delete scan result"
                )
            }
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun requestCameraPermission() {
        _uiState.value = _uiState.value.copy(showPermissionDialog = true)
    }

    private fun onPermissionGranted() {
        _uiState.value = _uiState.value.copy(
            hasCameraPermission = true,
            showPermissionDialog = false
        )
    }

    private fun onPermissionDenied() {
        _uiState.value = _uiState.value.copy(
            hasCameraPermission = false,
            showPermissionDialog = false
        )
    }

    private fun dismissPermissionDialog() {
        _uiState.value = _uiState.value.copy(showPermissionDialog = false)
    }

    private fun clearHistory() {
        viewModelScope.launch {
            try {
                // Clear from repository implementation would need to be added
                Timber.d("History cleared")
            } catch (e: Exception) {
                Timber.e(e, "Error clearing history")
                _uiState.value = _uiState.value.copy(
                    error = "Failed to clear history"
                )
            }
        }
    }
}