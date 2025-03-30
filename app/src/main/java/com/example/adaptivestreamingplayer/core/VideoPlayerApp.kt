package com.example.adaptivestreamingplayer.core

import android.app.Application
import android.os.Build
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.example.adaptivestreamingplayer.BuildConfig
import com.google.common.util.concurrent.FakeTimeLimiter
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class VideoPlayerApp : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())
        else
            Timber.plant(CrashReportingTree())
    }

    inner class CrashReportingTree: Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

            if(priority == Log.VERBOSE || priority == Log.DEBUG) return

            Timber.log(priority, tag, message)

            if(t != null) {
                if(priority == Log.ERROR) {
                    Timber.e(t)
                } else if(priority == Log.WARN) {
                    Timber.w(t)
                }
            }

        }
    }

    inner class CrashlyticsReportingTree:Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if(priority == Log.VERBOSE || priority == Log.DEBUG) return
        }

    }

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