package com.example.adaptivestreamingplayer.utils

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

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
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
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