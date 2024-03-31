package com.example.adaptivestreamingplayer.core

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
import com.example.adaptivestreamingplayer.jetlagged.JetLaggedScreen
import com.example.adaptivestreamingplayer.ktor.Service
import com.example.adaptivestreamingplayer.urlIssue.CloudFront
import com.example.playlist.PlaylistScreen

@Composable
fun Nav(
    onClickToILTSReports: () -> Unit,
    onClickToLogin: () -> Unit,
    onClickToVideoPlayer: () -> Unit,
    onClickToMemoryCard: () -> Unit,
    service: Service
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    NavHost(
        navController = navController,
        startDestination = Screen.HomeRoute.route
    ) {
        composable(Screen.HomeRoute.route){

            val toastMsg by remember {
                mutableStateOf("")
            }
            LaunchedEffect(key1 = toastMsg){
                Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
            }

            DummyButton(
                onClickToNavigatePlaylist = { navController.navigate(Screen.PlaylistScreenRoute.route) },
                onClickToNavigateCloudFront = { navController.navigate(Screen.CloudFrontScreenRoute.route) },
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
            JetLaggedScreen()
        }
        composable(Screen.CloudFrontScreenRoute.route){
            CloudFront(service = service, navController)
        }

        composable(Screen.PlaylistScreenRoute.route) {
            PlaylistScreen(service, navController)
        }

    }

}

sealed class Screen(val route: String) {
    data object HomeRoute: Screen("/homeRoute")
    data object ComposeVideoPlayerRoute: Screen("/composeVideoPlayerRoute")
    data object JetLaggedRoute: Screen("/jetLaggedRoute")
    data object CloudFrontScreenRoute: Screen("/cloudFrontScreenRoute")
    data object PlaylistScreenRoute: Screen("/playlistScreenRoute")
}