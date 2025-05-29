package com.adaptive.qrcodescanner.di

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideQRCodeScannerDatabase(
        @ApplicationContext context: Context
    ): QRCodeScannerDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            QRCodeScannerDatabase::class.java,
            QRCodeScannerDatabase.DATABASE_NAME
        ).build()
    }
    
    @Provides
    fun provideScanResultDao(database: QRCodeScannerDatabase): ScanResultDao {
        return database.scanResultDao()
    }
}