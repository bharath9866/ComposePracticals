package com.example.adaptivestreamingplayer.composePlayer

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.example.adaptivestreamingplayer.R

@OptIn(UnstableApi::class) @Composable
fun VideoPlayerCompose(modifier: Modifier = Modifier ) {
    val viewModel = hiltViewModel<MainViewModel>()

    val currentVideo by viewModel.currentVideo.collectAsState()
    val listOfVideos by viewModel.listOfVideos.collectAsState()

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            Log.d("lifeCycleVideoPlayerCompose", "$event")
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).also {
                    it.findViewById<PlayerView>(R.id.player_view)
                    it.player = viewModel.exoPlayer
                    it.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                        it.player?.play()
                    }

                    else -> Unit
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )
        Playlist(
            playListVideos = listOfVideos,
            currentVideo = currentVideo,
            onClickToPlayVideo = {
                viewModel.playVideo(it)
            }
        )
    }
}