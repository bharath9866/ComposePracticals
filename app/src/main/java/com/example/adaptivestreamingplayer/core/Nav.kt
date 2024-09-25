package com.example.adaptivestreamingplayer.core

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adaptivestreamingplayer.animation.ChainingAnimation
import com.example.adaptivestreamingplayer.animation.ItemPlacementComponents
import com.example.adaptivestreamingplayer.composePlayer.VideoPlayerScreen
import com.example.adaptivestreamingplayer.composeWebView.ComposeWebViewScreen
import com.example.adaptivestreamingplayer.jetlagged.JetLaggedScreen
import com.example.adaptivestreamingplayer.ktor.Service
import com.example.adaptivestreamingplayer.orderApp.presentation.OrderAppScreen
import com.example.adaptivestreamingplayer.urlIssue.CloudFront
import com.example.adaptivestreamingplayer.vernacular.VernacularMain
import com.example.playlist.PlaylistScreen

@Composable
fun Nav(
    navScreenActions:NavScreenActions = NavScreenActions(),
    service: Service
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.HomeRoute.route,
    ) {
        composable(Screen.HomeRoute.route){

            val toastMsg by remember {
                mutableStateOf("")
            }
            LaunchedEffect(key1 = toastMsg){
                Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
            }

            DummyButton(
                dummyButtonActions = DummyButtonActions(
                    testingILScreenActions = DummyButtonActions.TestingILScreenActions(
                        navigateToPlaylist = { navController.navigate(Screen.PlaylistScreenRoute.route) },
                        navigateToCloudFront = { navController.navigate(Screen.CloudFrontScreenRoute.route) },
                        navigateToILTSReport = { navScreenActions.navigateToILTSReports() },
                        navigateToLogin = { navScreenActions.navigateToLogin() },
                        navigateToVideoPlayer = { navScreenActions.navigateToVideoPlayer() },
                        navigateToMemoryCard = { navScreenActions.navigateToMemoryCard() },
                        navigateToComposePlayer = { navController.navigate(Screen.ComposeVideoPlayerRoute.route) },
                        navigateToProgressButton = { navScreenActions.navigateToProgressButton() },
                        navigateToVernacular = { navController.navigate(Screen.Vernacular.route) }
                    ),
                    experimentalScreenAction = DummyButtonActions.ExperimentalScreenActions(
                        navigateToJetLagged = { navController.navigate(Screen.JetLaggedRoute.route) },
                        navigateToChainingAnimation = { navController.navigate(Screen.ChainingAnimation.route) },
                        navigateToOrderApp = { navController.navigate(Screen.OrderAppRoute.route) },
                        navigateToItemPlacement = { navController.navigate(Screen.ItemPlacement.route) }
                    )
                )
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
        composable(Screen.OrderAppRoute.route) {
            OrderAppScreen()
        }

        composable(Screen.ComposeWebView.route){
            ComposeWebViewScreen()
        }
        composable(Screen.ChainingAnimation.route){
            ChainingAnimation()
        }
        composable(Screen.ItemPlacement.route){
            ItemPlacementComponents()
        }
        composable(Screen.Vernacular.route){
            VernacularMain()
        }
    }

}
//Testing Commit 2
sealed class Screen(val route: String) {
    data object HomeRoute: Screen("/homeRoute")
    data object ComposeVideoPlayerRoute: Screen("/composeVideoPlayerRoute")
    data object JetLaggedRoute: Screen("/jetLaggedRoute")
    data object CloudFrontScreenRoute: Screen("/cloudFrontScreenRoute")
    data object PlaylistScreenRoute: Screen("/playlistScreenRoute")
    data object OrderAppRoute: Screen("/OrderAppRoute")
    data object ComposeWebView: Screen("/ComposeWebViewRoute")
    data object ChainingAnimation: Screen("/ChainingAnimationRoute")
    data object ItemPlacement: Screen("/ItemPlacementRoute")
    data object Flashcards: Screen("/Flashcards")
    data object Vernacular: Screen("/Vernacular")
}