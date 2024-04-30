package com.example.adaptivestreamingplayer.composePlayer

import android.app.Activity
import android.app.FragmentContainer
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.databinding.ActivityPlayerBinding
import com.example.adaptivestreamingplayer.testCases.ui.ShoppingFragment

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    val activity = LocalContext.current as Activity

    var originalOrientation: Int? by remember { mutableStateOf(null) }

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
            Log.d("lifeCycleOnDispose", "onDispose")
//            originalOrientation?.let { activity.requestedOrientation = it }
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {


        AndroidViewBinding(
            factory = { inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean ->
                activityPlayerBinding(inflater, viewModel, navController)
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        this.playerView.onPause()
                        this.playerView.player?.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        this.playerView.onResume()
                        this.playerView.player?.play()
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

@OptIn(UnstableApi::class)
private fun activityPlayerBinding(
    inflater: LayoutInflater,
    viewModel: MainViewModel,
    navController: NavHostController
): ActivityPlayerBinding {
    val viewBinding = ActivityPlayerBinding.inflate(inflater)

    val playerView: PlayerView = viewBinding.playerView

    playerView.apply {
        player = viewModel.exoPlayer
        setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
    }

    viewBinding.playerView.findViewById<ImageButton>(R.id.exoBack).setOnClickListener {
        navController.popBackStack()
    }


    return viewBinding
}