package com.example.adaptivestreamingplayer.core

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.annotation.ExperimentalCoilApi
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import coil.util.Logger
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers

@HiltAndroidApp
class VideoPlayerApp : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.20)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(5 * 1024 * 1024)
                    .build()
            }
            .logger(DebugLogger())
            .dispatcher(Dispatchers.IO)
            .respectCacheHeaders(false)
            .build()
    }
}