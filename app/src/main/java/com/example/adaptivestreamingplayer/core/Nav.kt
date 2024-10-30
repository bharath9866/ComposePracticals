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
import com.example.adaptivestreamingplayer.NavigationOneScreen
import com.example.adaptivestreamingplayer.NavigationTwoScreen
import com.example.adaptivestreamingplayer.animation.ChainingAnimation
import com.example.adaptivestreamingplayer.animation.ItemPlacementComponents
import com.example.adaptivestreamingplayer.canvas.GetAllIcons
import com.example.adaptivestreamingplayer.composePlayer.VideoPlayerScreen
import com.example.adaptivestreamingplayer.composeUnstyledPracticals.ScrollAreaScreen
import com.example.adaptivestreamingplayer.composeWebView.ComposeWebViewScreen
import com.example.adaptivestreamingplayer.jetlagged.JetLaggedScreen
import com.example.adaptivestreamingplayer.ktor.Service
import com.example.adaptivestreamingplayer.orderApp.presentation.OrderAppScreen
import com.example.adaptivestreamingplayer.slThree.ComposeToAndroidView
import com.example.adaptivestreamingplayer.slThree.CreatePlanCard
import com.example.adaptivestreamingplayer.urlIssue.CloudFront
import com.example.adaptivestreamingplayer.vernacular.VernacularMain
import com.example.playlist.PlaylistScreen

@Composable
fun Nav(
    navScreenActions: NavScreenActions = NavScreenActions(),
    service: Service
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
    ) {
        composable<HomeRoute> {
            val toastMsg by remember {
                mutableStateOf("")
            }
            LaunchedEffect(key1 = toastMsg) {
                Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
            }

            DummyButton(
                dummyButtonActions = DummyButtonActions(
                    testingILScreenActions = DummyButtonActions.TestingILScreenActions(
                        navigateToPlaylist = { navController.navigate(PlaylistScreenRoute) },
                        navigateToCloudFront = { navController.navigate(CloudFrontScreenRoute) },
                        navigateToILTSReport = { navScreenActions.navigateToILTSReports() },
                        navigateToLogin = { navScreenActions.navigateToLogin() },
                        navigateToVideoPlayer = { navScreenActions.navigateToVideoPlayer() },
                        navigateToMemoryCard = { navScreenActions.navigateToMemoryCard() },
                        navigateToComposePlayer = { navController.navigate(ComposeVideoPlayerRoute) },
                        navigateToProgressButton = { navScreenActions.navigateToProgressButton() },
                        navigateToVernacular = { navController.navigate(Vernacular) },
                    ),
                    experimentalScreenAction = DummyButtonActions.ExperimentalScreenActions(
                        navigateToJetLagged = { navController.navigate(JetLaggedRoute) },
                        navigateToChainingAnimation = { navController.navigate(ChainingAnimation) },
                        navigateToOrderApp = { navController.navigate(OrderAppRoute) },
                        navigateToItemPlacement = { navController.navigate(ItemPlacement) },
                        navigateToTypeSafeNavigation = { id, name ->
                            navController.navigate(
                                NavigationOne(id, name)
                            )
                        },
                        navigateToComposeCanvasIcons = { navController.navigate(ComposeCanvasIcons) },
                        navigateToScrollArea = { navController.navigate(ScrollArea) },
                        navigateToDialogScreen = { navController.navigate(DialogScreen) },
                        navigateToCustomSpinner = { navScreenActions.navigateToCustomSpinner() },
                        navigateToCreatePlan = { navController.navigate(CreatePlan) },
                        navigateToCreatePlanActivity = { navScreenActions.navigateToCreatePlanActivity() },
                        navigateToRenderAndroidViewInCompose = { navController.navigate(ComposeToAndroidView) },
                    )
                )
            )

        }

        composable<ComposeVideoPlayerRoute> {
            VideoPlayerScreen(Modifier, navController)
        }

        composable<JetLaggedRoute> {
            JetLaggedScreen()
        }
        composable<CloudFrontScreenRoute> {
            CloudFront(service = service, navController)
        }

        composable<PlaylistScreenRoute> {
            PlaylistScreen(service, navController)
        }
        composable<OrderAppRoute> {
            OrderAppScreen()
        }

        composable<ComposeWebView> {
            ComposeWebViewScreen()
        }
        composable<ChainingAnimation> {
            ChainingAnimation()
        }
        composable<ItemPlacement> {
            ItemPlacementComponents()
        }
        composable<Vernacular> {
            VernacularMain()
        }

        composable<NavigationOne> {
            NavigationOneScreen(
                navController = navController,
                navBackStackEntry = it
            )
        }
        composable<NavigationTwo> {
            NavigationTwoScreen(
                navController = navController,
                navBackStackEntry = it
            )
        }

        composable<ComposeCanvasIcons> {
            GetAllIcons()
        }

        composable<ScrollArea> {
            ScrollAreaScreen()
        }
        composable<CreatePlan> {
            CreatePlanCard()
        }
        composable<ComposeToAndroidView> {
            ComposeToAndroidView()
        }
    }

}