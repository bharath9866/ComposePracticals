package com.example.adaptivestreamingplayer.core

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.ilts.report.ILTSReportActivity
import com.example.adaptivestreamingplayer.ktor.Service
import com.example.adaptivestreamingplayer.ktor.dto.LoginRequest
import com.example.adaptivestreamingplayer.memoryCard.screens.MemoryFlashCardsActivity
import com.example.adaptivestreamingplayer.player.PlayerActivity
import com.example.adaptivestreamingplayer.search.SearchBar
import com.example.adaptivestreamingplayer.utils.Constants
import com.example.adaptivestreamingplayer.utils.SLSharedPreference
import com.example.adaptivestreamingplayer.utils.SLSharedPreference.accessToken
import com.example.adaptivestreamingplayer.utils.SLSharedPreference.setLoginData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val service: Service = Service.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SLSharedPreference.instance = getSharedPreferences(
            Constants.SL_SHAREDPREF,
            MODE_PRIVATE
        )

         setContent {
             val scope = rememberCoroutineScope()
             var toastMsg by remember { mutableStateOf("") }
             Nav(
                 service = service,
                 onClickToILTSReports = {
                     startActivity(Intent(applicationContext, ILTSReportActivity::class.java))
                 },
                 onClickToVideoPlayer = {
                     startActivity(Intent(applicationContext, PlayerActivity::class.java))
                 },
                 onClickToMemoryCard = {
                     startActivity(Intent(applicationContext, MemoryFlashCardsActivity::class.java))
                 },
                 onClickToLogin = {
                     scope.launch(Dispatchers.IO) {
                         val i = service.createPost(loginRequest = LoginRequest("Dummy0307", "test123"))
                         i?.let { setLoginData(it) }
                         accessToken = i?.accessToken?:""
                         toastMsg = "$i"
                     }
                 }
             )
         }
    }
}


@Composable
fun DummyButton(
    onClickToNavigateOrderApp: () -> Unit,
    onClickToNavigatePlaylist: () -> Unit,
    onClickToNavigateCloudFront: () -> Unit,
    onClickToJetLagged: () -> Unit,
    onClickToILTSReports: () -> Unit,
    onClickToLogin: () -> Unit,
    onClickToVideoPlayer: () -> Unit,
    onClickToMemoryCard: () -> Unit,
    onClickComposePlayer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar()
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            onClick = onClickToNavigateOrderApp
        ) {
            Text(
                text = "OrderApp",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            onClick = onClickToNavigatePlaylist
        ) {
            Text(
                text = "Navigate To Playlist",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            onClick = onClickToNavigateCloudFront
        ) {
            Text(
                text = "Navigate To CloudFront",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            onClick = onClickToJetLagged
        ) {
            Text(
                text = "Jet Lagged",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            onClick = onClickToILTSReports
        ) {
            Text(
                text = "ILTS Reports",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            onClick = onClickToLogin
        ) {
            Text(
                text = "Login",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            onClick = onClickToVideoPlayer
        ) {
            Text(
                text = "Play Video",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            onClick = onClickToMemoryCard
        ) {
            Text(
                text = "Memory Card",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            onClick = onClickComposePlayer
        ) {
            Text(
                text = "Compose Video Player",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}