package com.example.adaptivestreamingplayer

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.memoryCard.screens.FlashCardsAdapter
import com.example.adaptivestreamingplayer.memoryCard.screens.MemoryFlashCardsActivity
import com.example.adaptivestreamingplayer.player.PlayerActivity
import com.example.utils.Constants
import com.example.utils.SLSharedPreference

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SLSharedPreference.instance = getSharedPreferences(
            Constants.SL_SHAREDPREF,
            MODE_PRIVATE
        )

        setContent {
            DummyButton(
                onClickToVideoPlayer = {
                    startActivity(Intent(applicationContext, PlayerActivity::class.java))
                },
                onClickToMemoryCard = {
                    startActivity(Intent(applicationContext, MemoryFlashCardsActivity::class.java))
                }
            )
        }
    }
}


@Composable
fun DummyButton(
    onClickToVideoPlayer: () -> Unit,
    onClickToMemoryCard: () -> Unit,
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
    }
}