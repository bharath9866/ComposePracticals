package com.example.adaptivestreamingplayer

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adaptivestreamingplayer.composePlayer.VideoPlayerScreen
import com.example.adaptivestreamingplayer.jetlagged.JetLagged

@Composable
fun Nav(
    onClickToILTSReports: () -> Unit,
    onClickToLogin: () -> Unit,
    onClickToVideoPlayer: () -> Unit,
    onClickToMemoryCard: () -> Unit,
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    NavHost(
        navController = navController,
        startDestination = Screen.HomeRoute.route
    ) {
        composable(Screen.HomeRoute.route){

            var toastMsg by remember {
                mutableStateOf("")
            }
            LaunchedEffect(key1 = toastMsg){
                Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
            }

            DummyButton(
                onClickToJetLagged = { navController.navigate(Screen.JetLaggedRoute.route) },
                onClickToILTSReports = {onClickToILTSReports()},
                onClickToLogin = { onClickToLogin() },
                onClickToVideoPlayer = { onClickToVideoPlayer() },
                onClickToMemoryCard = { onClickToMemoryCard() },
                onClickComposePlayer = { navController.navigate(Screen.ComposeVideoPlayerRoute.route) }
            )

        }

        composable(Screen.ComposeVideoPlayerRoute.route) {
            VideoPlayerScreen(Modifier, navController)
        }

        composable(Screen.JetLaggedRoute.route){
            JetLagged()
        }
    }

}

sealed class Screen(val route: String) {
    data object HomeRoute:Screen("/homeRoute")
    data object ComposeVideoPlayerRoute:Screen("/composeVideoPlayerRoute")
    data object JetLaggedRoute:Screen("/jetLaggedRoute")
}