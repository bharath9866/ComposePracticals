package com.adaptive.qrcodescanner.di

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindScanResultRepository(
        scanResultRepositoryImpl: ScanResultRepositoryImpl
    ): ScanResultRepository
}