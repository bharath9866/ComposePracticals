package com.example.adaptivestreamingplayer.core

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.adaptivestreamingplayer.canvas.Light_mode
import com.example.adaptivestreamingplayer.chatReaction.ChatReactionActivity
import com.example.adaptivestreamingplayer.customComponent.CustomComponentActivity
import com.example.adaptivestreamingplayer.facebookReactions.sample.ReactionSampleActivity
import com.example.adaptivestreamingplayer.ilts.report.ILTSReportActivity
import com.example.adaptivestreamingplayer.ktor.Service
import com.example.adaptivestreamingplayer.ktor.dto.LoginRequest
import com.example.adaptivestreamingplayer.memoryCard.screens.MemoryFlashCardsActivity
import com.example.adaptivestreamingplayer.onBoarding.ProgressButtonView
import com.example.adaptivestreamingplayer.player.PlayerActivity
import com.example.adaptivestreamingplayer.search.SearchBar
import com.example.adaptivestreamingplayer.slThree.CreatePlanActivity
import com.example.adaptivestreamingplayer.utils.Constants
import com.example.adaptivestreamingplayer.utils.SLSharedPreference
import com.example.adaptivestreamingplayer.utils.SLSharedPreference.accessToken
import com.example.adaptivestreamingplayer.utils.SLSharedPreference.setLoginData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TransparentSystemUIApp(content: @Composable () -> Unit){
    androidx.compose.material3.Surface(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize(),
        color = Color.Transparent // Transparent app background if needed
    ) {
        content()
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val service: Service = Service.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.statusBars())
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // Set transparent system bars
        // window.statusBarColor = Color.Transparent.toArgb()
        // window.navigationBarColor = Color.Transparent.toArgb()
        // // For API 30+ use window.setDecorFitsSystemWindows directly
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        //     window.setDecorFitsSystemWindows(false)
        //     // Set light or dark icons in system bar
        //     window.insetsController?.setSystemBarsAppearance(
        //         WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
        //         WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        //     )
        // } else {
        //     // Fallback for older APIs
        //     @Suppress("DEPRECATION")
        //     window.decorView.systemUiVisibility = (
        //             View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
        //                     View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
        //                     View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        //             )
        // }

        SLSharedPreference.instance = getSharedPreferences(
            Constants.SL_SHAREDPREF,
            MODE_PRIVATE
        )
        enableEdgeToEdge()
        setContent {
            TransparentSystemUIApp {
                val scope = rememberCoroutineScope()
                var toastMsg by remember { mutableStateOf("") }
                Nav(
                    service = service,
                    navScreenActions = NavScreenActions(
                        navigateToILTSReports = {
                            startActivity(Intent(applicationContext, ILTSReportActivity::class.java))
                        },
                        navigateToVideoPlayer = {
                            startActivity(Intent(applicationContext, PlayerActivity::class.java))
                        },
                        navigateToMemoryCard = {
                            startActivity(
                                Intent(
                                    applicationContext,
                                    MemoryFlashCardsActivity::class.java
                                )
                            )
                        },
                        navigateToLogin = {
                            scope.launch(Dispatchers.IO) {
                                val i =
                                    service.createPost(
                                        loginRequest = LoginRequest(
                                            "Dummy0307",
                                            "test123"
                                        )
                                    )
                                i?.let { setLoginData(it) }
                                accessToken = i?.accessToken ?: ""
                                toastMsg = "$i"
                            }
                        },
                        navigateToProgressButton = {
                            startActivity(Intent(applicationContext, ProgressButtonView::class.java))
                        },
                        navigateToCustomSpinner = {
                            startActivity(
                                Intent(
                                    applicationContext,
                                    CustomComponentActivity::class.java
                                )
                            )
                        },
                        navigateToCreatePlanActivity = {
                            startActivity(Intent(applicationContext, CreatePlanActivity::class.java))
                        },
                        navigateToFaceBookMainActivity = {
                            startActivity(
                                Intent(
                                    applicationContext,
                                    ReactionSampleActivity::class.java
                                )
                            )
                        },
                        navigateToChatReactionActivity = {
                            startActivity(Intent(applicationContext, ChatReactionActivity::class.java))
                        }
                    ),
                )
            }
        }
    }
}

/**
 * @param dummyButtonActions is a action Event which have multiple click events
 * [DummyButtonActions] which have properties of
 * [DummyButtonActions.testingILScreenActions] and [DummyButtonActions.experimentalScreenAction]
 * @return this function a Unit
 */
@Preview
@Composable
fun DummyButton(dummyButtonActions: DummyButtonActions = DummyButtonActions()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Light_mode,
            contentDescription = "Light Mode",
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            colorFilter = ColorFilter.tint(Color.Black)
        )
        SearchBar()
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            onClick = dummyButtonActions.testingILScreenActions.navigateToToolTip
        ) {
            Text(
                text = "ToolTip",
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
            onClick = dummyButtonActions.testingILScreenActions.navigateToNotification
        ) {
            Text(
                text = "Notifications",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToFilterChip
        ) {
            Text(
                text = "FilterChipDropDown",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToCoroutineScreen
        ) {
            Text(
                text = "Coroutine Screen",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToJetLagged
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToHomeWidgetList
        ) {
            Text(
                text = "Home Widget List",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToChatReactionActivity
        ) {
            Text(
                text = "ChatReaction",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToFaceBookMainActivity
        ) {
            Text(
                text = "FaceBook MainActivity Reaction",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToRenderAndroidViewInCompose
        ) {
            Text(
                text = "Render Android View in Compose",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToCreatePlanActivity

        ) {
            Text(
                text = "Create Plan XML Activity",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToCreatePlan

        ) {
            Text(
                text = "Create Plan",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToPageFlip

        ) {
            Text(
                text = "Page Flip",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToCustomSpinner

        ) {
            Text(
                text = "Custom Spinner",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToScrollArea

        ) {
            Text(
                text = "Scroll Area",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToDialogScreen

        ) {
            Text(
                text = "Dialog Screen",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToComposeCanvasIcons

        ) {
            Text(
                text = "Canvas Icons in Compose",
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
            onClick = {
                dummyButtonActions.experimentalScreenAction.navigateToTypeSafeNavigation(
                    1,
                    "NavigationOneScreen"
                )
            }
        ) {
            Text(
                text = "Type Safe Navigation",
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
            onClick = dummyButtonActions.testingILScreenActions.navigateToVernacular
        ) {
            Text(
                text = "Vernacular",
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
            onClick = dummyButtonActions.testingILScreenActions.navigateToProgressButton
        ) {
            Text(
                text = "Progress Button",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToItemPlacement
        ) {
            Text(
                text = "Item Placement",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToChainingAnimation
        ) {
            Text(
                text = "Chaining Animation",
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
            onClick = dummyButtonActions.experimentalScreenAction.navigateToOrderApp
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
            onClick = dummyButtonActions.testingILScreenActions.navigateToPlaylist
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
            onClick = dummyButtonActions.testingILScreenActions.navigateToCloudFront
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
            onClick = dummyButtonActions.testingILScreenActions.navigateToILTSReport
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
            onClick = dummyButtonActions.testingILScreenActions.navigateToLogin
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
            onClick = dummyButtonActions.testingILScreenActions.navigateToVideoPlayer
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
            onClick = dummyButtonActions.testingILScreenActions.navigateToMemoryCard
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
            onClick = dummyButtonActions.testingILScreenActions.navigateToComposePlayer
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
