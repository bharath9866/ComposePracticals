package com.example.adaptivestreamingplayer.composePlayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.adaptivestreamingplayer.testingToJson.Video
import com.example.adaptivestreamingplayer.utils.convertSecToMin


@Composable
fun Playlist(
    modifier: Modifier = Modifier,
    playListVideos: List<Video>,
    currentVideo: Video?,
    onClickToPlayVideo: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .background(Color(0xFFFFFFFF))
            .verticalScroll(scrollState)
    ) {
        //==============================================================================================================
        PlaylistHeader(
            modifier = Modifier,
            currentVideo = currentVideo
        )
        //==============================================================================================================
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.6.dp,
            color = Color(0xFFD9DBE9)
        )
        //==============================================================================================================
        PlaylistTitle()
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
        )
        //==============================================================================================================
        playListVideos.forEachIndexed { index, video ->
            PlaylistVideoCard(
                videoTitle = playListVideos[index].title ?: "--",
                duration = convertSecToMin(playListVideos[index].durationinsecs.toLong()),
                content = contentForVideoPlaying(
                    isVideoPlaying = {
                        playListVideos[index].videoId == currentVideo?.videoId
                    },
                    duration = playListVideos[index].duration ?: "0"
                ),
                onClickToPlayVideo = onClickToPlayVideo,
                videoId = playListVideos[index].videoId
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}