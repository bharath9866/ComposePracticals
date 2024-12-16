package com.example.adaptivestreamingplayer.playlist

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
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.adaptivestreamingplayer.testingToJson.Video
import com.example.adaptivestreamingplayer.ui.theme.appFontFamily
import com.github.imagecoil.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PlaylistScreen(service: Service, navController: NavHostController) {
    var list:ArrayList<Video>? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()
    Column {
        TopAppBar(
            callPlaylistAPI = {
                coroutineScope.launch(Dispatchers.IO) {
                    list = service.getPlaylist(examId = 1, subTenantId = 45, playlistTypeId = 4)?.data?.first()?.videos
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
private fun PlaylistBody(list: ArrayList<Video>? = arrayListOf()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(list?.size ?: 0, key = { it }) {
            Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {

                AsyncImage(
                    painter = list?.get(it)?.thumbnailURL,
                    error = R.drawable.ic_default_subject_icon,
                    placeholder = R.drawable.ic_default_subject_icon,
                    contentDescription = "Subject Icon"
                )

                Text(text = list?.get(it)?.title ?:"", fontSize = 11.sp, color = Color(0xFF000000), fontWeight = FontWeight.Bold)
                Log.d("directory", "${context.imageLoader.memoryCache?.keys}")
            }
        }
    }
}