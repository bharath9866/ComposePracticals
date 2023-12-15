package com.example.adaptivestreamingplayer

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.adaptivestreamingplayer.databinding.ActivityPlayerBinding

class MainActivity : ComponentActivity() {

    private var player: ExoPlayer? = null

    private var playWhenReady = true
    private var mediaItemIndex = 0
    private var playbackPosition = 0L

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPlayerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

    public override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 24) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Build.VERSION.SDK_INT <= 24 || player == null) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 24) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 24) {
            releasePlayer()
        }
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            viewBinding.videoView.player = exoPlayer

            val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.playWhenReady = playWhenReady
            exoPlayer.prepare()
        }
    }

    private fun releasePlayer() {
        player?.let { player ->
            playbackPosition = player.currentPosition
            mediaItemIndex = player.currentMediaItemIndex
            playWhenReady = player.playWhenReady
        }
        player = null
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, viewBinding.videoView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}