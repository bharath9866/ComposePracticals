package com.example.adaptivestreamingplayer.composePlayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.testingToJson.Video
import com.example.adaptivestreamingplayer.ui.theme.appFontFamily

@Composable
fun PlaylistHeader(modifier: Modifier = Modifier, currentVideo:Video?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = currentVideo?.title?:"",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = appFontFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF14142B)
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        VideoFeedback(
            isVideoLiked = feedbackEvent(true, "100"),
            onLike = {},
            onDisLike = {}
        )
    }
}

@Preview
@Composable
private fun VideoFeedbackPreview() {
    VideoFeedback(feedbackEvent(true, "100"), {}) {}
}

@Composable
private fun VideoFeedback(
    isVideoLiked: FeedbackObject,
    onLike: (Boolean) -> Unit,
    onDisLike: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FeedbackButton(
            feedbackDrawable = isVideoLiked.like.drawable,
            count = isVideoLiked.like.count,
            onClick = onLike,
            feedbackStatus = isVideoLiked.like.likeStatus
        )
        FeedbackButton(
            feedbackDrawable = isVideoLiked.disLike.drawable,
            count = isVideoLiked.disLike.count,
            onClick = onDisLike,
            feedbackStatus = isVideoLiked.disLike.likeStatus
        )
    }
}

@Composable
private fun FeedbackButton(
    feedbackDrawable: Int,
    count: String,
    onClick:(Boolean) -> Unit,
    feedbackStatus:Boolean
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(width = 0.6.dp, color = Color(0xFFD9DBE9), shape = CircleShape)
                .size(40.dp)
                .background(color = Color(0xFFFCFCFC))
                .clickable { onClick(!feedbackStatus) },
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = feedbackDrawable),
                contentDescription = "Like Button"
            )
        }
        Text(
            text = count,
            style = TextStyle(
                fontSize = 11.sp,
                lineHeight = 14.sp,
                fontFamily = appFontFamily,
                fontWeight = FontWeight(500),
                color = Color(0xFF4E4B66),
                textAlign = TextAlign.Center,
                letterSpacing = 0.1.sp,
            ),
            maxLines = 1
        )
    }
}

private fun feedbackEvent(isVideoLiked:Boolean?, count:String):FeedbackObject {
    return when(isVideoLiked) {
        true -> {
            FeedbackObject(
                like = FeedbackObject.Like(
                    drawable = R.drawable.ic_like,
                    count = count,
                    likeStatus = true,
                ),
                disLike = FeedbackObject.DisLike(
                    drawable = R.drawable.ic_thumbsdown,
                    count = "DisLike",
                    likeStatus = false,
                )
            )
        }
        false -> {
            FeedbackObject(
                like = FeedbackObject.Like(
                    drawable = R.drawable.ic_thumbsup,
                    count = count,
                    likeStatus = false,
                ),
                disLike = FeedbackObject.DisLike(
                    drawable = R.drawable.ic_dislike,
                    count = "DisLike",
                    likeStatus = true,
                )
            )
        }
        else -> {
            FeedbackObject(
                like = FeedbackObject.Like(
                    drawable = R.drawable.ic_thumbsup,
                    count = count,
                    likeStatus = false,
                ),
                disLike = FeedbackObject.DisLike(
                    drawable = R.drawable.ic_thumbsdown,
                    count = "DisLike",
                    likeStatus = false,
                )
            )
        }
    }
}

data class FeedbackObject(
    val like: Like,
    val disLike: DisLike
) {
    data class Like(val drawable:Int, val count:String, val likeStatus:Boolean)
    data class DisLike(val drawable:Int, val count:String, val likeStatus:Boolean)
}