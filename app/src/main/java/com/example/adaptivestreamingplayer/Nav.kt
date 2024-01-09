package com.example.adaptivestreamingplayer

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adaptivestreamingplayer.composePlayer.VideoPlayerScreen

@Composable
fun Nav(
    onClickToLogin: () -> Unit,
    onClickToVideoPlayer: () -> Unit,
    onClickToMemoryCard: () -> Unit,
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route){

            var toastMsg by remember {
                mutableStateOf("")
            }
            LaunchedEffect(key1 = toastMsg){
                Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
            }
            val scope = rememberCoroutineScope()
            DummyButton(
                onClickToLogin = { onClickToLogin() },
                onClickToVideoPlayer = { onClickToVideoPlayer() },
                onClickToMemoryCard = { onClickToMemoryCard() },
                onClickComposePlayer = {
                    navController.navigate(Screen.ComposeVideoPlayer.route)
                }
            )

        }

        composable(Screen.ComposeVideoPlayer.route) {
            VideoPlayerScreen(Modifier, navController)
        }
    }

}

sealed class Screen(val route: String) {
    data object HomeScreen:Screen("/homeScreen")
    data object ComposeVideoPlayer:Screen("/composeVideoPlayer")
}