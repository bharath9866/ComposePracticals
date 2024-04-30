package com.example.adaptivestreamingplayer

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import com.github.imagecoil.extractVideoId

@SuppressLint("LogNotTimber")
@Composable
fun AsyncImage(
    modifier: Modifier = Modifier,
    painter: String?,
    contentDescription: String? = null,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null,
    @DrawableRes fallback: Int? = error,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    numberOfRetries:Int? = 3
) {

    var retryHash by remember { mutableIntStateOf(0) }

    coil.compose.AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .setParameter("retry_hash", retryHash)
            .data(painter)
            .build(),
        contentDescription = contentDescription,
        imageLoader = LocalContext.current.imageLoader,
        modifier = modifier,
        placeholder = placeholder?.let { painterResource(id = it) },
        error = error?.let { painterResource(id = it) },
        fallback = fallback?.let { painterResource(id = it) },
        onLoading = onLoading,
        onSuccess = onSuccess,
        onError = onError ?: run {
            {
                numberOfRetries?.let { if (retryHash < it) retryHash++ } ?: retryHash++
                Log.d(
                    "onErrorAsyncImage",
                    "${painter.extractVideoId()} - $retryHash - $numberOfRetries"
                )
            }
        },
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality
    )
}