@file:OptIn(ExperimentalAnimationApi::class)

package com.example.adaptivestreamingplayer.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.ui.theme.appFontFamily
import com.example.adaptivestreamingplayer.utils.ImageVector
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

// "videos", "test", "flashcard", "subject", "live class"
@Preview
@Composable
fun SearchBar() {

    val coroutineScope = rememberCoroutineScope()

    val mSearchEventsList by remember { mutableStateOf(arrayListOf("videos", "topic summaries", "chapter & topics")) }

    val collection by mSearchEventsList.infiniteLoopOfList(coroutineScope).collectAsState(initial = "")

    Box(modifier = Modifier.padding(16.dp)) {
        SearchRow(
            function = { collection }
        )
    }
}

@ExperimentalAnimationApi
fun addAnimation(duration: Int = 2000): ContentTransform {
    return (
            slideInVertically(
                animationSpec = tween(durationMillis = duration)
            ) { height ->
                -height
            } + fadeIn(animationSpec = tween(durationMillis = duration))
            ).togetherWith(
            slideOutVertically(
                animationSpec = tween(durationMillis = duration)
            ) { height ->
                +height
            } + fadeOut(animationSpec = tween(durationMillis = duration))
        )
}

@Composable
fun SearchRow(function: () -> String) {

    var flag by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = flag) {
        if(flag){
            delay(1000)
            flag = true
        }
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 7.dp)
            .wrapContentHeight()
            .fillMaxWidth()
            .then(
                if (true) {
                    Modifier.animatedBorder(
                        borderColors = listOf(Color(0xFF3C8DCB), Color(0xFF3C8DCB), Color.White),
                        backgroundColor = Color.Transparent,
                        shape = RoundedCornerShape(8.dp),
                        borderWidth = 1.dp
                    )
                } else {
                    Modifier.border(0.5.dp, color = Color(0xFFD9DBE9), RoundedCornerShape(8.dp))
                }
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFFCFCFC))
            .padding(12.dp)
        ,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageVector(imageVector = R.drawable.search_icon)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Search for ",
            fontFamily = appFontFamily,
            color = Color(0xFF6E7191),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 16.sp,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.width(4.dp))

        AnimatedContent(
            targetState = function(),
            transitionSpec = {
                addAnimation().using(
                    SizeTransform(clip = false)
                )
            }, label = ""
        ) { targetCount ->
            Text(
                text = targetCount,
                fontFamily = appFontFamily,
                color = Color(0xFF14142B),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 16.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}

fun Modifier.animatedBorder(
    borderColors: List<Color> = listOf(Color(0xFF3C8DCB)),
    backgroundColor: Color = Color.Transparent,
    shape: Shape = RectangleShape,
    borderWidth: Dp = 1.dp,
    animationDurationInMillis: Int = 1000,
    easing: Easing = LinearEasing
): Modifier = composed {
    val brush = Brush.sweepGradient(borderColors)
    val infiniteTransition = rememberInfiniteTransition(label = "animatedBorder")
    val angle by infiniteTransition.animateFloat(
        initialValue = 184f,
        targetValue = -184f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDurationInMillis, easing = easing),
            repeatMode = RepeatMode.Restart,
        ), label = "angleAnimation"
    )

    this
        .clip(shape)
        .padding(borderWidth)
        .drawWithContent {
            rotate(angle) {
                drawCircle(
                    brush = brush,
                    radius = size.width,
                    blendMode = BlendMode.SrcIn,
                )
            }
            drawContent()
        }
        .background(color = backgroundColor, shape = shape)
}