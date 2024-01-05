package com.example.adaptivestreamingplayer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.adaptivestreamingplayer.memoryCard.model.BookMarkedMemoryCardResponse
import com.example.adaptivestreamingplayer.memoryCard.screens.FlashCardsAdapter
import com.example.adaptivestreamingplayer.memoryCard.screens.MemoryFlashCardsActivity
import com.example.adaptivestreamingplayer.player.PlayerActivity
import com.example.adaptivestreamingplayer.testingToJson.GetVideos
import com.example.ktor.Service
import com.example.ktor.dto.LoginRequest
import com.example.utils.Constants
import com.example.utils.SLSharedPreference
import com.example.utils.readJSONFromAssets
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val service: Service = Service.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SLSharedPreference.instance = getSharedPreferences(
            Constants.SL_SHAREDPREF,
            MODE_PRIVATE
        )


        setContent {

            var toastMsg by remember() {
                mutableStateOf("")
            }
            LaunchedEffect(key1 = toastMsg){
                Toast.makeText(this@MainActivity, toastMsg, Toast.LENGTH_SHORT).show()
            }
            val scope = rememberCoroutineScope()
            DummyButton(
                onClickToLogin = {
                    scope.launch(Dispatchers.IO) {
                        toastMsg = "${service.createPost(postRequest = LoginRequest("Dummy0307", "test123"))}"
                    }
                },
                onClickToVideoPlayer = {
                    startActivity(Intent(applicationContext, PlayerActivity::class.java))
                },
                onClickToMemoryCard = {
                    startActivity(Intent(applicationContext, MemoryFlashCardsActivity::class.java))
                },
                onClickToPrintJson = {
                    val json = readJSONFromAssets(this, "video_list.json")
                    Log.d("toJsonToJson", json)
                    val getVideos = Gson().fromJson(json, GetVideos::class.java)
                    Log.d("toJsonObject", "$getVideos")
                    val againToJson = Gson().toJson(getVideos, GetVideos::class.java)
                    Log.d("toJsonAgainToJson", againToJson)
                }
            )
        }
    }
}


@Composable
fun DummyButton(
    onClickToLogin: () -> Unit,
    onClickToVideoPlayer: () -> Unit,
    onClickToMemoryCard: () -> Unit,
    onClickToPrintJson: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            onClick = onClickToPrintJson
        ) {
            Text(
                text = "Print Json",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}