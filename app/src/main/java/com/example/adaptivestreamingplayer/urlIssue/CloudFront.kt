package com.example.adaptivestreamingplayer.urlIssue

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.imageLoader
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.ktor.Service
import com.example.adaptivestreamingplayer.ui.theme.appFontFamily
import com.github.imagecoil.extractVideoId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.github.imagecoil.AsyncImage

@Composable
fun CloudFront(service: Service, navController: NavHostController) {
    var list by remember {
        mutableStateOf<List<String>>(listOf())
    }
    val coroutineScope = rememberCoroutineScope()
    Column {
        TopAppBar(
            callPlaylistAPI = {
                coroutineScope.launch(Dispatchers.IO) {
                   var lcList= service.getSubjects(examId = 1, subTenantId = 45, tenantId = 2, status = "active")?.data?.childNodes?.mapNotNull { it.icon }
                    list = List(20) {
                        lcList?: listOf()
                    }.flatten()
                }
            },
            popBackStack = {
                navController.popBackStack()
            }
        )
        PlaylistBody(list)
    }
}

@Preview
@Composable
private fun TopAppBar(callPlaylistAPI: () -> Unit = {}, popBackStack:() -> Unit = {}) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .clickable { popBackStack() },
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = "Back"
        )
        Text(
            text = "Call API",
            modifier = Modifier
                .padding(16.dp)
                .clickable { callPlaylistAPI() },
            fontFamily = appFontFamily,
            color = Color(0xFF000000),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun PlaylistBody(list: List<String> = arrayListOf()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LazyVerticalGrid(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(), columns = GridCells.Fixed(4)) {
        items(list.size, key = {it}) {
            Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {

                val url = list[it]

                AsyncImage(
                    painter = url,
                    error = R.drawable.ic_default_subject_icon,
                    placeholder = R.drawable.ic_default_subject_icon,
                    contentDescription = "Subject Icon"
                )

                Text(text = list[it].extractVideoId(), fontSize = 11.sp, color = Color(0xFF000000), fontWeight = FontWeight.Bold)
                Log.d("directory", "${context.imageLoader.memoryCache?.keys}")
            }
        }
    }
}

//@Composable
//fun AsyncImage(
//    modifier: Modifier = Modifier,
//    imageUrl: String?,
//    contentDescription: String? = null,
//    @DrawableRes placeholder: Int? = null,
//    @DrawableRes error: Int? = null,
//    @DrawableRes fallback: Int? = error,
//    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
//    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
//    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
//    alignment: Alignment = Alignment.Center,
//    contentScale: ContentScale = ContentScale.Fit,
//    alpha: Float = DefaultAlpha,
//    colorFilter: ColorFilter? = null,
//    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
//    numberOfRetries:Int? = 3
//) {
//
//    var retryHash by remember { mutableIntStateOf(0) }
//
//    AsyncImage(
//        model = ImageRequest
//            .Builder(LocalContext.current)
//            .setParameter("retry_hash", retryHash)
//            .data(imageUrl)
//            .build(),
//        contentDescription = contentDescription,
//        imageLoader = LocalContext.current.imageLoader,
//        modifier = modifier,
//        placeholder = placeholder?.let { painterResource(id = it) },
//        error = error?.let { painterResource(id = it) },
//        fallback = fallback?.let { painterResource(id = it) },
//        onLoading = onLoading,
//        onSuccess = onSuccess,
//        onError = onError ?: run {
//            {
//                numberOfRetries?.let { if (retryHash < it) retryHash++ } ?: retryHash++
//                Log.d("onErrorAsyncImage", "${imageUrl.extractVideoId()} - $retryHash - $numberOfRetries")
//            }
//        }
//        ,
//        alignment = alignment,
//        contentScale = contentScale,
//        alpha = alpha,
//        colorFilter = colorFilter,
//        filterQuality = filterQuality
//    )
//}
//
//fun String?.extractVideoId(): String {
//    val primaryUrl = this
//    return primaryUrl?.split("?")?.get(0)?.split("/")?.last()?:""
//}