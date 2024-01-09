package com.example.adaptivestreamingplayer.player

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.annotation.OptIn
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.ui.PlayerView
import androidx.media3.ui.TrackSelectionDialogBuilder
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.databinding.ActivityPlayerBinding
import com.example.adaptivestreamingplayer.testingToJson.VideoPlaylistResponse
import com.example.adaptivestreamingplayer.utils.readJSONFromAssets
import com.google.gson.Gson


@OptIn(UnstableApi::class)
class PlayerActivity : ComponentActivity() {

    private var exoPlayer: ExoPlayer? = null
    private lateinit var playerView: PlayerView
    private lateinit var exoQuality: ImageButton

    private var playWhenReady = true
    private var currentMediaItemIndex = 0
    private var playbackPosition = 0L

    private lateinit var trackSelector: DefaultTrackSelector
    private val playbackStateListener: Player.Listener = playbackStateListener()

    private var trackDialog: Dialog? = null

    private lateinit var listOfVideoUrls: List<String>


    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPlayerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val json = readJSONFromAssets(this, "playlist.json")
        val playlistIds = Gson().fromJson(json, VideoPlaylistResponse::class.java)
        listOfVideoUrls = playlistIds.data?.flatMap { it -> it.videos?.map { it.videoURL } ?: emptyList() }?.filterNotNull()?: emptyList()

        playerView = findViewById(R.id.player_view)
        exoQuality = playerView.findViewById(R.id.exo_quality)

        exoQuality.setOnClickListener {
            if (trackDialog == null) initPopupQuality()
            trackDialog?.show()
        }

        Log.d("lifeCycle", "onCreate")
    }

    public override fun onStart() {
        super.onStart()
        Log.d("lifeCycle", "onStart")
        if (Build.VERSION.SDK_INT > 24) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        Log.d("lifeCycle", "onResume")
        if (Build.VERSION.SDK_INT <= 24 || exoPlayer == null) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        Log.d("lifeCycle", "onPause")
        if (Build.VERSION.SDK_INT <= 24) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        Log.d("lifeCycle", "onStop")
        if (Build.VERSION.SDK_INT > 24) {
            releasePlayer()
        }
    }


    private fun initializePlayer() {
        trackSelector = DefaultTrackSelector(this).apply {
            setParameters(buildUponParameters().setMaxVideoSizeSd())
        }

        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()


        exoPlayer =
            ExoPlayer.Builder(this).setTrackSelector(trackSelector).build().also { exoPlayer ->
                viewBinding.playerView.player = exoPlayer

//                val mediaItem = MediaItem.Builder().setUri(getString(R.string.tutorix_two_m3u8))
//                    .setMimeType(MimeTypes.APPLICATION_M3U8).build()
//                exoPlayer.setMediaItem(mediaItem)


                val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp4))
                val secondMediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))


                exoPlayer.setMediaItems(listOfVideoUrls.map { MediaItem.fromUri(it) }, currentMediaItemIndex, playbackPosition)

                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentMediaItemIndex, playbackPosition)
                exoPlayer.addListener(playbackStateListener)
                exoPlayer.prepare()
            }
    }

    private fun releasePlayer() {
        exoPlayer?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentMediaItemIndex = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.removeListener(playbackStateListener)
            exoPlayer.release()
        }
        exoPlayer = null
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, viewBinding.playerView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun initPopupQuality() {
        exoPlayer?.let { exoPlayer ->
            val trackSelectionDialogBuilder = TrackSelectionDialogBuilder(
                /* context = */ this,
                /* title = */ getString(R.string.qualitySelector),
                /* player = */ exoPlayer,
                /* trackType = */ C.TRACK_TYPE_VIDEO
            )
            trackSelectionDialogBuilder.setTrackNameProvider {
                getString(R.string.exo_track_resolution_pixel, it.height)
            }
            trackDialog = trackSelectionDialogBuilder.build()
        }
    }

}

private const val TAG = "PlayerActivity"


fun playbackStateListener() = object : Player.Listener {
    override fun onPlaybackStateChanged(playbackState: Int) {
        val stateString: String = when (playbackState) {
            ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE"
            ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING"
            ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY"
            ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_END"
            else -> "UNKNOWN STATE"
        }

        Log.d(TAG, "changed state to $stateString")
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        val playingString: String = if (isPlaying) "PLAYING" else "NOT PLAYING"
        Log.d(TAG, "player is currently $playingString")
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        Log.d("onMediaItemTransition", "mediaItem: ${mediaItem?.mediaId}, reason: $reason")
    }
    
}