package com.example.adaptivestreamingplayer.utils

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.core.content.ContextCompat

fun isTabletOrMobile(ctx: Context): Boolean {
    return (ctx.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
}

/**
 * This Function is able to send parameters as Seconds and Milliseconds
 * 1. if We are passing Seconds Then isSeconds = true (by default isSeconds = true)
 * 2. if we are passing Milliseconds then is Seconds = false
 */
fun convertSecToTimeFormat(
    parentSeconds: Long,
    mode: String,
    isSeconds: Boolean = true
): String {
    val childSeconds = if (isSeconds) {
        parentSeconds
    } else {
        parentSeconds / 1000
    }

    val seconds = childSeconds % 60
    val minutes = childSeconds / 60 % 60
    val hours = childSeconds / 3600

    val s = if (seconds <= 9) "0" else ""
    val m = if (minutes <= 9) "0" else ""
    val h = if (hours <= 9) "0" else ""

    val sec = if (seconds > 1) "secs" else "sec"
    val min = if (minutes > 1) "mins" else "min"
    val hour = if (hours > 1) "hours" else "hour"

    return if (childSeconds != 0L) {
        when (mode) {
            "HHh MMm SSs" -> {
                if (hours == 0L)
                    "$m${minutes}m $s${seconds}s"
                else
                    "$h${hours}h $m${minutes}m"
            }
            "HH:MM:SSs" -> {
                if (hours == 0L)
                    "$m${minutes}:$s${seconds}s"
                else
                    "$h${hours}:$m${minutes}:$s${seconds}s"
            }
            "hh:mm:ss" -> {
                if (hours != 0L)
                    "$h${hours}:$m${minutes}:$s${seconds}"
                else
                    "$m${minutes}:$s${seconds}"
            }
            "Hh Mm Ss" -> {
                if (hours != 0L)
                    "${hours}h ${minutes}m"
                else
                    "${minutes}m ${seconds}s"
            }
            "Hh Mins" -> {
                if (hours != 0L)
                    "${hours}hr ${minutes}mins"
                else
                    "${minutes}mins"
            }
            "mm:ss ss" -> {
                if (hours == 0L && minutes == 0L)
                    "$m${minutes}:$s${seconds} $sec"
                else if (hours == 0L)
                    "$m${minutes}:$s${seconds} $min"
                else
                    "$h${hours}:$m${minutes} $hour"
            }
            else -> "00:00"
        }
    } else {
        when (mode) {
            "HH:MM:SSs" -> "00:00s"
            "hh:mm:ss" -> "00:00"
            "Hh Mm Ss" -> "0m 0s"
            "mm:ss ss" -> "00:00 sec"
            "HHh MMm SSs" -> "--"
            else -> ""
        }
    }

}


fun convertSecToMin(seconds: Long): String {
    val sec = seconds % 60
    val min = seconds / 60
    return if (sec < 10) "$min mins 0$sec sec" else "$min mins $sec sec"
}

fun <T> List<T>.toArrayList(): ArrayList<T> = ArrayList(this)


@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun ImageVector(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    @DrawableRes imageVector: Int = 0,
    contentAlignment: Alignment = Alignment.Center,
    contentDescription: String? = "",
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment,
    ) {
        if(imageVector!=0) {
            Image(
                imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(imageVector),
                contentDescription = contentDescription,
                modifier = imageModifier,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            )
        }
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "Infinite Shimmer Transition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1200)
        ),
        label = "Shimmer Animation"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned { size = it.size }
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        border = border,
        interactionSource = interactionSource
    ) {
        Row(
            Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

fun Modifier.onClickWithoutRipple(onClick: () -> Unit): Modifier = this.clickable(
    indication = null,
    interactionSource = NoRippleInteractionSource(),
    onClick = { onClick() }
)

class NoRippleInteractionSource() : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}


@Preview(showBackground = true, device = Devices.PIXEL, fontScale = 1.0f, name = "Pixel Default (100%)", group = "Pixel")
@Preview(showBackground = true, device = Devices.PIXEL, fontScale = 0.85f, name = "Pixel Small (85%)", group = "Pixel")
@Preview(showBackground = true, device = Devices.PIXEL, fontScale = 1.15f, name = "Pixel Large (115%)", group = "Pixel")
@Preview(showBackground = true, device = Devices.PIXEL, fontScale = 1.3f, name = "Pixel Largest (130%)", group = "Pixel")
annotation class PreviewPixel

@Preview(showBackground = true, device = Devices.NEXUS_7, fontScale = 1.0f, name = "NEXUS_7 Default (100%)", group = "NEXUS_7")
@Preview(showBackground = true, device = Devices.NEXUS_7, fontScale = 0.85f, name = "NEXUS_7 Small (85%)", group = "NEXUS_7")
@Preview(showBackground = true, device = Devices.NEXUS_7, fontScale = 1.15f, name = "NEXUS_7 Large (115%)", group = "NEXUS_7")
@Preview(showBackground = true, device = Devices.NEXUS_7, fontScale = 1.3f, name = "NEXUS_7 Largest (130%)", group = "NEXUS_7")
annotation class PreviewNexusSeven

@Preview(showBackground = true, device = Devices.NEXUS_10, fontScale = 1.0f, name = "NEXUS_10 Default (100%)", group = "NEXUS_10")
@Preview(showBackground = true, device = Devices.NEXUS_10, fontScale = 0.85f, name = "NEXUS_10 Small (85%)", group = "NEXUS_10")
@Preview(showBackground = true, device = Devices.NEXUS_10, fontScale = 1.15f, name = "NEXUS_10 Large (115%)", group = "NEXUS_10")
@Preview(showBackground = true, device = Devices.NEXUS_10, fontScale = 1.3f, name = "NEXUS_10 Largest (130%)", group = "NEXUS_10")
annotation class PreviewNexusTen

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240", fontScale = 1.0f, name = "TABLET Default (100%)", group = "TABLET")
@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240", fontScale = 0.85f, name = "TABLET Small (85%)", group = "TABLET")
@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240", fontScale = 1.15f, name = "TABLET Large (115%)", group = "TABLET")
@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240", fontScale = 1.3f, name = "TABLET Largest (130%)", group = "TABLET")
annotation class PreviewTablet

@Preview(showBackground = true, device = "spec:width=673dp,height=841dp,dpi=420", fontScale = 1.0f, name = "TABLET Default (100%)", group = "TABLET")
@Preview(showBackground = true, device = "spec:width=673dp,height=841dp,dpi=420", fontScale = 0.85f, name = "TABLET Small (85%)", group = "TABLET")
@Preview(showBackground = true, device = "spec:width=673dp,height=841dp,dpi=420", fontScale = 1.15f, name = "TABLET Large (115%)", group = "TABLET")
@Preview(showBackground = true, device = "spec:width=673dp,height=841dp,dpi=420", fontScale = 1.3f, name = "TABLET Largest (130%)", group = "TABLET")
annotation class PreviewDesktop

@PreviewPixel
@PreviewNexusSeven
@PreviewNexusTen
@PreviewTablet
annotation class PreviewAll

@Preview(showBackground = true, device = Devices.DEFAULT, name = "Default", group = "device")
@Preview(showBackground = true, device = Devices.PIXEL, name = "Pixel", group = "device")
@Preview(showBackground = true, device = Devices.PIXEL_XL, name = "Pixel_XL", group = "device")
@Preview(showBackground = true, device = Devices.NEXUS_7, name = "NEXUS_7", group = "device")
@Preview(showBackground = true, device = Devices.NEXUS_10, name = "Nexus_10", group = "device")
annotation class PreviewDevices

@Preview(showBackground = true, fontScale = 1.0f, name = "Default (100%)", group = "FontScale")
@Preview(showBackground = true, fontScale = 0.85f, name = "Small (85%)", group = "FontScale")
@Preview(showBackground = true, fontScale = 1.15f, name = "Large (115%)", group = "FontScale")
@Preview(showBackground = true, fontScale = 1.3f, name = "Largest (130%)", group = "FontScale")
annotation class PreviewFontScale

fun createAnnotatedString(
    context: Context,
    parts: List<TextOrDrawable>
): SpannableStringBuilder {
    return SpannableStringBuilder().also { spannableBuilder ->
        parts.forEach { part ->
            when (part) {
                is TextOrDrawable.Text -> spannableBuilder.append(part.text)
                is TextOrDrawable.DrawableRes -> {
                    ContextCompat.getDrawable(context, part.drawableResId)?.apply {
                        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                    }?.let {
                        val imageSpan = ImageSpan(it, ImageSpan.ALIGN_BOTTOM)
                        spannableBuilder.append(" ", imageSpan, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                }
            }
        }
    }
}

sealed class TextOrDrawable {
    data class Text(val text: String) : TextOrDrawable()
    data class DrawableRes(@androidx.annotation.DrawableRes val drawableResId: Int) : TextOrDrawable()
}

enum class StrokeAlignment {
    INNER, CENTER, OUTER
}

//fun Modifier.strokeBorder(
//    color: Color,
//    strokeWidth: Dp,
//    shape: Shape = RectangleShape,
//    alignment: StrokeAlignment = StrokeAlignment.CENTER
//): Modifier = this.then(
//    Modifier.drawWithContent {
//        drawContent()
//
//        val strokePx = strokeWidth.toPx()
//        val inset = when (alignment) {
//            StrokeAlignment.INNER -> strokePx / 2
//            StrokeAlignment.CENTER -> 0f
//            StrokeAlignment.OUTER -> -strokePx / 2
//        }
//
//        val outline = shape.createOutline(size, layoutDirection, this)
//
//        val path = when (outline) {
//            is Outline.Rectangle -> Path().apply { addRect(outline.rect.inflate(inset)) }
//            is Outline.Rounded -> Path().apply { addRoundRect(outline.roundRect.inflateRoundRect(inset)) }
//            is Outline.Generic -> Path().apply { addPath(outline.path) } // limited control
//        }
//
//        drawPath(
//            path = path,
//            color = color,
//            style = Stroke(width = strokePx)
//        )
//    }
//)

private fun Rect.inflateRect(amount: Float): Rect {
    return Rect(
        left = left - amount,
        top = top - amount,
        right = right + amount,
        bottom = bottom + amount
    )
}

//private fun RoundRect.inflateRoundRect(amount: Float): RoundRect {
//    val newRect = Rect(
//        left = left - amount,
//        top = top - amount,
//        right = right + amount,
//        bottom = bottom + amount
//    )
//
//    return RoundRect(
//        rect = newRect,
//        topLeft = CornerRadius(topLeft.x + amount, topLeft.y + amount),
//        topRight = CornerRadius(topRight.x + amount, topRight.y + amount),
//        bottomRight = CornerRadius(bottomRight.x + amount, bottomRight.y + amount),
//        bottomLeft = CornerRadius(bottomLeft.x + amount, bottomLeft.y + amount)
//    )
//}