package com.adaptive.qrcodescanner

@HiltAndroidApp
class QRCodeScannerApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
