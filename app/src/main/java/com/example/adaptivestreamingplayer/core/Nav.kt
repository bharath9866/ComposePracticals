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
import androidx.navigation.toRoute
import com.example.adaptivestreamingplayer.NavigationOneScreen
import com.example.adaptivestreamingplayer.NavigationTwoScreen
import com.example.adaptivestreamingplayer.animation.ChainingAnimation
import com.example.adaptivestreamingplayer.animation.ItemPlacementComponents
import com.example.adaptivestreamingplayer.api.ApiScreen
import com.example.adaptivestreamingplayer.canvas.GetAllIcons
import com.example.adaptivestreamingplayer.canvas.ToolTip
import com.example.adaptivestreamingplayer.composePlayer.VideoPlayerScreen
import com.example.adaptivestreamingplayer.composeUnstyledPracticals.ScrollAreaScreen
import com.example.adaptivestreamingplayer.composeWebView.ComposeWebViewScreen
import com.example.adaptivestreamingplayer.coroutines.CoroutineHomeScreen
import com.example.adaptivestreamingplayer.coroutines.coroutineBasics.homework.EasyAssignmentOne
import com.example.adaptivestreamingplayer.coroutines.coroutineBasics.homework.HardAssignmentThree
import com.example.adaptivestreamingplayer.coroutines.coroutineBasics.homework.MediumAssignmentTwo
import com.example.adaptivestreamingplayer.coroutines.coroutineContext.homework.EasyAssignmentOneCoroutineContext
import com.example.adaptivestreamingplayer.coroutines.coroutineContext.homework.assignmentTwo.MediumAssignmentTwoCoroutineContext
import com.example.adaptivestreamingplayer.coroutines.coroutineContext.whatIsCoroutineContext.WhatIsCoroutineContext
import com.example.adaptivestreamingplayer.coroutines.coroutineContext.withContext.WithCoroutineContextScreen
import com.example.adaptivestreamingplayer.filterChip.FilterChipDropDown
import com.example.adaptivestreamingplayer.gSmart.otp.OTPScreenRoute
import com.example.adaptivestreamingplayer.gSmart.onBoarding.OnBoardingScreenRoute
import com.example.adaptivestreamingplayer.imagePicker.PhotoPickerScreen
import com.example.adaptivestreamingplayer.jetlagged.JetLaggedScreen
import com.example.adaptivestreamingplayer.jetlagged.SleepBarPreview
import com.example.adaptivestreamingplayer.ktor.Service
import com.example.adaptivestreamingplayer.notification.NotificationScreen
import com.example.adaptivestreamingplayer.orderApp.presentation.OrderAppScreen
import com.example.adaptivestreamingplayer.playlist.PlaylistScreen
import com.example.adaptivestreamingplayer.slThree.ComposeToAndroidView
import com.example.adaptivestreamingplayer.slThree.CreatePlanCard
import com.example.adaptivestreamingplayer.smartHome.fan.FenasonicScreen
import com.example.adaptivestreamingplayer.smartHome.lamp.LightLampScreen
import com.example.adaptivestreamingplayer.stroke.StrokeBrushHome
import com.example.adaptivestreamingplayer.stroke.StrokeHomeScreen
import com.example.adaptivestreamingplayer.stroke.strokeText.StrokeTextHome
import com.example.adaptivestreamingplayer.ui.theme.JetLaggedTheme
import com.example.adaptivestreamingplayer.urlIssue.CloudFront
import com.example.adaptivestreamingplayer.vernacular.VernacularMain

@Composable
fun Nav(
    navScreenActions: NavScreenActions = NavScreenActions(),
    service: Service
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = AppRoute.HomeRoute,
    ) {
        composable<AppRoute.GSmartOnBoarding> {
            OnBoardingScreenRoute(navController)
        }
        composable<AppRoute.OTPScreen> {
            val args by remember {
                mutableStateOf(it.toRoute<AppRoute.OTPScreen>())
            }
            OTPScreenRoute(
                navController = navController,
                isFor = args.isFor,
                phoneNumber = args.phoneNumber
            )
        }
        composable<AppRoute.HomeRoute> {
            val toastMsg by remember {
                mutableStateOf("")
            }
            LaunchedEffect(key1 = toastMsg) {
                Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
            }

            DummyButton(
                dummyButtonActions = DummyButtonActions(
                    testingILScreenActions = DummyButtonActions.TestingILScreenActions(
                        navigateToPlaylist = { navController.navigate(AppRoute.PlaylistScreenRoute) },
                        navigateToCloudFront = { navController.navigate(AppRoute.CloudFrontScreenRoute) },
                        navigateToILTSReport = { navScreenActions.navigateToILTSReports() },
                        navigateToLogin = { navScreenActions.navigateToLogin() },
                        navigateToVideoPlayer = { navScreenActions.navigateToVideoPlayer() },
                        navigateToMemoryCard = { navScreenActions.navigateToMemoryCard() },
                        navigateToComposePlayer = { navController.navigate(AppRoute.ComposeVideoPlayerRoute) },
                        navigateToProgressButton = { navScreenActions.navigateToProgressButton() },
                        navigateToVernacular = { navController.navigate(AppRoute.Vernacular) },
                        navigateToNotification = { navController.navigate(AppRoute.NotificationScreenRoute) },
                        navigateToToolTip = { navController.navigate(AppRoute.ToolTipRoute) },
                        navigateToApiScreen = { navController.navigate(AppRoute.ApiScreenRoute) },
                    ),
                    experimentalScreenAction = DummyButtonActions.ExperimentalScreenActions(
                        navigateToJetLagged = { navController.navigate(AppRoute.JetLaggedRoute) },
                        navigateToChainingAnimation = { navController.navigate(AppRoute.ChainingAnimation) },
                        navigateToOrderApp = { navController.navigate(AppRoute.OrderAppRoute) },
                        navigateToItemPlacement = { navController.navigate(AppRoute.ItemPlacement) },
                        navigateToTypeSafeNavigation = { id, name ->
                            navController.navigate(
                                AppRoute.NavigationOne(id, name)
                            )
                        },
                        navigateToComposeCanvasIcons = { navController.navigate(AppRoute.ComposeCanvasIcons) },
                        navigateToScrollArea = { navController.navigate(AppRoute.ScrollArea) },
                        navigateToDialogScreen = { navController.navigate(AppRoute.DialogScreen) },
                        navigateToCustomSpinner = { navScreenActions.navigateToCustomSpinner() },
                        navigateToCreatePlan = { navController.navigate(AppRoute.CreatePlan) },
                        navigateToCreatePlanActivity = { navScreenActions.navigateToCreatePlanActivity() },
                        navigateToRenderAndroidViewInCompose = { navController.navigate(AppRoute.ChainingAnimation) },
                        navigateToFaceBookMainActivity = { navScreenActions.navigateToFaceBookMainActivity() },
                        navigateToChatReactionActivity = { navScreenActions.navigateToChatReactionActivity() },
                        navigateToHomeWidgetList = { navScreenActions.navigateToHomeWidgetList },
                        navigateToCoroutineScreen = { navController.navigate(AppRoute.CoroutineScreenRoute) },
                        navigateToFilterChip = { navController.navigate(AppRoute.FilterChipDropDownRoute) },
                    )
                )
            )

        }

        composable<AppRoute.ComposeVideoPlayerRoute> {
            VideoPlayerScreen(Modifier, navController)
        }

        composable<AppRoute.JetLaggedRoute> {
            JetLaggedTheme {
                JetLaggedScreen()
            }
        }
        composable<AppRoute.CloudFrontScreenRoute> {
            CloudFront(service = service, navController)
        }

        composable<AppRoute.PlaylistScreenRoute> {
            PlaylistScreen(service, navController)
        }
        composable<AppRoute.OrderAppRoute> {
            OrderAppScreen()
        }

        composable<AppRoute.ComposeWebView> {
            ComposeWebViewScreen()
        }
        composable<AppRoute.ChainingAnimation> {
            ChainingAnimation()
        }
        composable<AppRoute.ItemPlacement> {
            ItemPlacementComponents()
        }
        composable<AppRoute.Vernacular> {
            VernacularMain()
        }

        composable<AppRoute.NavigationOne> {
            NavigationOneScreen(
                navController = navController,
                navBackStackEntry = it
            )
        }
        composable<AppRoute.NavigationTwo> {
            NavigationTwoScreen(
                navController = navController,
                navBackStackEntry = it
            )
        }

        composable<AppRoute.ComposeCanvasIcons> {
            GetAllIcons()
        }

        composable<AppRoute.ScrollArea> {
            ScrollAreaScreen()
        }
        composable<AppRoute.CreatePlan> {
            CreatePlanCard()
        }
        composable<AppRoute.ComposeToAndroidView> {
            ComposeToAndroidView()
        }
        composable<AppRoute.SleepBarRoute> {
            SleepBarPreview()
        }
        composable<AppRoute.StrokeHomeRoute> {
            StrokeHomeScreen(navController = navController)
        }
        composable<AppRoute.StrokeTextRoute> {
            StrokeTextHome()
        }
        composable<AppRoute.StrokeBrushRoute> {
            StrokeBrushHome()
        }
        composable<AppRoute.CoroutineScreenRoute> {
            CoroutineHomeScreen { navController.navigate(it) }
        }
        composable<AppRoute.EasyAssignmentOne> {
            EasyAssignmentOne()
        }
        composable<AppRoute.MediumAssignmentTwo> {
            MediumAssignmentTwo()
        }
        composable<AppRoute.HardAssignmentThree> {
            HardAssignmentThree()
        }
        composable<AppRoute.WhatIsCoroutineContextRoute> {
            WhatIsCoroutineContext()
        }
        composable<AppRoute.WithCoroutineContextRoute> {
            WithCoroutineContextScreen()
        }
        composable<AppRoute.EasyAssignmentOneCoroutineContextRoute> {
            EasyAssignmentOneCoroutineContext()
        }
        composable<AppRoute.MediumAssignmentTwoCoroutineContextRoute> {
            MediumAssignmentTwoCoroutineContext()
        }
        composable<AppRoute.FilterChipDropDownRoute> {
            FilterChipDropDown()
        }
        composable<AppRoute.NotificationScreenRoute> {
            NotificationScreen(navController = navController)
        }

        composable<AppRoute.ToolTipRoute> {
            ToolTip()
        }
        composable<AppRoute.LightLamp> {
            LightLampScreen()
        }
        composable<AppRoute.Fan> {
            FenasonicScreen()
        }
        composable<AppRoute.PhotoPickerRoute> {
            PhotoPickerScreen()
        }

        composable<AppRoute.ApiScreenRoute> {
            ApiScreen()
        }
    }

}