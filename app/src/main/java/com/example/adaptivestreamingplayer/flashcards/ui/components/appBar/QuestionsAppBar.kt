package com.example.adaptivestreamingplayer.flashcards.ui.components.appBar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.ui.theme.appFontFamily
import com.example.adaptivestreamingplayer.utils.ImageVector


@Preview
@Composable
fun QuestionsAppBar(
    modifier: Modifier = Modifier,
    closeButton: () -> Unit = {},
    cardTopPadding: (IntSize) -> Unit = {},
    progress: () -> Float = { 0f },
    topicName: String = "Topic Name",
    isLinearProgressEnabled:Boolean = true,
    linearProgressIndicator: @Composable ColumnScope.(Float) -> Unit = {
        this.LinearProgressIndicator(progress = it)
    }
) {
    Column(modifier = modifier
        .background(Color(0xFF4E4B66))
        .fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ImageVector(
                    modifier = Modifier
                        .padding(vertical = 19.dp, horizontal = 16.dp)
                        .size(24.dp)
                        .clickable { closeButton() },
                    imageModifier = Modifier,
                    imageVector = R.drawable.ic_cross,
                    contentAlignment = Alignment.Center,
                    colorFilter = ColorFilter.tint(color = Color(0xFFFCFCFC))
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    androidx.compose.material.Text(
                        text = topicName,
                        color = Color(0xFFFCFCFC),
                        modifier = Modifier,
                        fontSize = 18.sp,
                        fontFamily = appFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Left,
                        lineHeight = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(vertical = 19.dp, horizontal = 16.dp)
                        .size(24.dp)
                )

            }
        }
        if(isLinearProgressEnabled) linearProgressIndicator(0.8f)
    }
}

@Composable
internal fun ColumnScope.LinearProgressIndicator(modifier: Modifier = Modifier, progress: Float = 0f) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "Animated Progress Float Value for Linear Progress Indicator"
    )
    Spacer(modifier = Modifier
        .height(2.dp)
        .fillMaxWidth())
    LinearProgressIndicator(
        progress = animatedProgress,
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp)),
        color = Color(0XFFFBD323),
        backgroundColor = Color(0xFFFCFCFC),
        strokeCap = StrokeCap.Round,
    )
}