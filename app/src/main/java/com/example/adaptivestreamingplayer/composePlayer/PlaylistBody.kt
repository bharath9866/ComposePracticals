package com.example.adaptivestreamingplayer.composePlayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.ui.theme.appFontFamily

@Preview
@Composable
fun PlaylistTitle() {
    Row(
        modifier = Modifier
            .background(Color(0xFFFAFCFF))
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier.padding(top = 19.dp),
            verticalArrangement = Arrangement.spacedBy(17.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Playlist",
                modifier = Modifier.wrapContentWidth(),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontFamily = appFontFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF00354E),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.25.sp,
                )
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .width(51.dp),
                thickness = 2.dp,
                color = Color(0xFF00354E)
            )
        }
    }
}


@Composable
fun PlaylistVideoCard(
    modifier:Modifier = Modifier,
    videoTitle:String,
    duration:String,
    content: Content,
    onClickToPlayVideo:(Int) -> Unit,
    videoId:Int
) {


    Row(
        modifier = Modifier
            then (modifier)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .then(
                Modifier.background(content.backGroundColorOfCard)
            )
            .clickable { onClickToPlayVideo(videoId) }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .background(color = Color(0xFFEFF0F6)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_black_video_icon),
                contentDescription = "Video Status",
                colorFilter = ColorFilter.tint(content.iconColor)
            )
        }

        Spacer(Modifier.width(16.dp))

        Column {
            Text(
                text = videoTitle,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = appFontFamily,
                    fontWeight = content.titleTextWeight,
                    color = content.titleTextColor,
                )
            )
            Text(
                text = content.duration,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    fontFamily = appFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF6E7191),
                )
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PlaylistVideoCardPreview() {
    Box(modifier = Modifier.padding(16.dp)){
        PlaylistVideoCard(
            videoTitle = "Video Title",
            duration = "04: 22 mins",
            content = contentForVideoPlaying({ true }, "100"),
            videoId = 0,
            onClickToPlayVideo = {}
        )
    }
}
fun contentForVideoPlaying(isVideoPlaying: () -> Boolean, duration:String) :Content {
    return if(isVideoPlaying())
        Content(
            titleTextColor = Color(0xFF3C8DCB),
            backGroundColorOfCard = Color(0xFFD8EDFE),
            iconColor = Color(0xFF3C8DCB),
            duration = "Now Playing",
            titleTextWeight = FontWeight(600)
        )
    else Content(
        titleTextColor = Color(0xFF14142A),
        backGroundColorOfCard = Color(0xFFFCFCFC),
        iconColor = Color(0xFF6E7191),
        duration = duration,
        titleTextWeight = FontWeight(400)
    )
}

data class Content(
    val titleTextColor:Color,
    val titleTextWeight:FontWeight,
    val backGroundColorOfCard:Color,
    val iconColor:Color,
    val duration: String
)