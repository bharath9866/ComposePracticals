package com.example.adaptivestreamingplayer.composePlayer

import android.app.Application
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import com.example.adaptivestreamingplayer.player.playbackStateListener
import com.example.adaptivestreamingplayer.testingToJson.Video
import com.example.adaptivestreamingplayer.testingToJson.VideoPlaylistResponse
import com.example.adaptivestreamingplayer.utils.readJSONFromAssets
import com.example.adaptivestreamingplayer.utils.toArrayList
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val application: Application,
    val exoPlayer: Player,
) : ViewModel() {
    private val sampleVideo = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    private val _currentVideo = MutableStateFlow<Video?>(null)
    val currentVideo = _currentVideo.asStateFlow()

    private val _listOfVideos = MutableStateFlow(arrayListOf<Video>())
    val listOfVideos = _listOfVideos.asStateFlow()

    private fun setlistOfVideos(value: () -> ArrayList<Video>) {
        _listOfVideos.value = value()
    }

    private fun setcurrentVideo(value: () -> Video?) {
        _currentVideo.value = value()
    }


    init {
        val json = readJSONFromAssets(application.applicationContext, "playlist.json")
        val playlist = Gson().fromJson(json, VideoPlaylistResponse::class.java)

        val videoList = playlist.data?.flatMap { videoPlaylistData ->
            videoPlaylistData.videos?.toList() ?: listOf()
        } ?: listOf()

        setlistOfVideos { videoList.toArrayList() }

        if(videoList.lastIndex>=0){
            setcurrentVideo { videoList[0] }
        }

        exoPlayer.prepare()
        exoPlayer.addMediaItem(MediaItem.fromUri(currentVideo.value?.videoURL?:""))
        exoPlayer.playWhenReady
        exoPlayer.addListener(playbackStateListener())
        //exoPlayer.seekTo(180000)
        exoPlayer.play()
    }

    fun addVideoUri(uri: Uri) {
        exoPlayer.addMediaItem(MediaItem.fromUri(uri))
    }

    fun playVideo(videoId: Int) {
        listOfVideos.value.find { it.videoId == videoId }?.also { video ->
            setcurrentVideo { video }
            exoPlayer.setMediaItem(
                MediaItem.fromUri(video.videoURL?:"")
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }
}

data class VideoItem(
    val contentUri: Uri,
    val mediaItem: MediaItem,
    val name: String
)