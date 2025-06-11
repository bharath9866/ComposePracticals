package com.example.adaptivestreamingplayer.gSmart.onBoarding

import android.app.Activity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.core.AppRoute
import com.example.adaptivestreamingplayer.gSmart.onBoarding.onBoardingBottomSheet.BottomSheetConfigs
import com.example.adaptivestreamingplayer.gSmart.onBoarding.onBoardingBottomSheet.BottomSheetType
import com.example.adaptivestreamingplayer.gSmart.onBoarding.onBoardingBottomSheet.DynamicBottomSheet
import com.example.adaptivestreamingplayer.gSmart.onBoarding.onBoardingBottomSheet.SheetShape
import timber.log.Timber

val CreateAccountTextColor = Color(0xFF1B1A40) // Dark blue
val LoginTextColor = Color(0xFFFFDE69) // Yellow
val OnBoardingBackgroundColor = Color(0xFF050522) // Dark blue background
val FigmaIndicatorActiveColor = Color.White
val FigmaIndicatorInactiveColor = Color.Gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreenRoute(navController: NavHostController? = null) {
    var currentBottomSheet by remember { mutableStateOf(BottomSheetType.NONE) }
    val pagerState = rememberPagerState { pages.size }
    //TransparentStatusBar(appBackgroundColor = pages[pagerState.currentPage].backgroundColor)
    OnBoardingScreen(
        onGetStartedClicked = {
            println("Get Started Clicked!")
        },
        onCreateAccountClicked = {
            currentBottomSheet = BottomSheetType.REGISTER
        },
        onLoginClicked = {
            currentBottomSheet = BottomSheetType.LOGIN
        },
        pagerState = pagerState
    )

    when (currentBottomSheet) {
        BottomSheetType.REGISTER -> {
            DynamicBottomSheet(
                config = BottomSheetConfigs.registerConfig(),
                modifier = Modifier,
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                shape = SheetShape,
                onDismiss = { currentBottomSheet = BottomSheetType.NONE },
                onButtonClick = {
                    Timber.tag("OnBoardingScreenRoute:REGISTER:onButtonClick").d("$it")
                    currentBottomSheet = BottomSheetType.NONE
                    it["mobile"].takeIf { !it.isNullOrEmpty() }?.let {
                        navController?.navigate(AppRoute.OTPScreen(BottomSheetType.REGISTER, it))
                    }
                },
                onFooterLinkClick = {
                    currentBottomSheet = BottomSheetType.LOGIN
                    Timber.tag("OnBoardingScreenRoute:REGISTER:onFooterLinkClick").d("")
                }
            )
        }

        BottomSheetType.LOGIN -> {
            DynamicBottomSheet(
                config = BottomSheetConfigs.loginConfig(),
                modifier = Modifier,
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                shape = SheetShape,
                onDismiss = { currentBottomSheet = BottomSheetType.NONE },
                onButtonClick = {
                    currentBottomSheet = BottomSheetType.NONE
                    it["mobile"].takeIf { !it.isNullOrEmpty() }?.let {
                        navController?.navigate(AppRoute.OTPScreen(BottomSheetType.LOGIN, it))
                    }
                    Timber.tag("OnBoardingScreenRoute:LOGIN:onButtonClick").d("$it")
                },
                onFooterLinkClick = {
                    currentBottomSheet = BottomSheetType.REGISTER
                    Timber.tag("OnBoardingScreenRoute:LOGIN:onFooterLinkClick").d("")
                }
            )
        }

        BottomSheetType.NONE -> {
            // No bottom sheet shown
        }
    }
}

@Composable
fun OnBoardingScreen(
    onGetStartedClicked: () -> Unit,
    onCreateAccountClicked: () -> Unit,
    onLoginClicked: () -> Unit,
    pagerState: PagerState
) {
    var bottomSectionSize by remember { mutableStateOf(IntSize.Zero) }

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars) // Add status bar padding
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        if (bottomSectionSize.height > 0)
                            (LocalView.current.height - bottomSectionSize.height).dp
                        else 0.dp
                    ) // Adjust height based on bottom section
                    .padding(innerPadding)
            ) { pageIndex ->
                val pageData = pages[pageIndex]
                OnBoardingPageContent(
                    modifier = Modifier.fillMaxSize().background(pageData.backgroundColor),
                    pageData = pageData
                )
            }
            OnBoardingBottomSection(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .onGloballyPositioned { coordinates ->
                        bottomSectionSize = coordinates.size
                    },
                pagerState = pagerState,
                pageCount = pages.size,
                onCreateAccountClicked = onCreateAccountClicked,
                onLoginClicked = onLoginClicked,
            )
        }
    }
}


@Composable
fun OnBoardingPageContent(
    modifier: Modifier = Modifier,
    pageData: OnBoardingPageData
) {
    Column(
        modifier = modifier
            .padding(horizontal = 32.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(pageData.imageResource),
            contentDescription = pageData.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(bottom = 48.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = pageData.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFEF5858),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = pageData.description,
            fontSize = 16.sp,
            color = Color(0xFFF4F4F4),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingBottomSection(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pageCount: Int,
    onCreateAccountClicked:() -> Unit,
    onLoginClicked:() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, bottom = 71.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        PageIndicator(
            pageCount = pageCount,
            currentPage = pagerState.currentPage,
            activeColor = FigmaIndicatorActiveColor,
            inactiveColor = FigmaIndicatorInactiveColor,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Button(
            onClick = onCreateAccountClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LoginTextColor,
                contentColor = CreateAccountTextColor
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Create Account",
                color = CreateAccountTextColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Button(
            onClick = onLoginClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(3.dp, LoginTextColor, RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = LoginTextColor
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Login",
                color = LoginTextColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    activeColor: Color,
    inactiveColor: Color,
    modifier: Modifier = Modifier,
    indicatorSize: Dp = 8.dp, // Size of indicators from Figma
    spacing: Dp = 8.dp // Spacing between indicators from Figma
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Preview
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(
        onGetStartedClicked = {},
        onCreateAccountClicked = {},
        onLoginClicked = {},
        pagerState = rememberPagerState { pages.size }
    )
}

@Composable
fun TransparentStatusBar(appBackgroundColor: Color) {
    val view = LocalView.current
    LaunchedEffect(appBackgroundColor) {
        val window = (view.context as Activity).window
        configureTransparentStatusBarForBackground(
            window = window,
            view = view,
            appBackgroundColor = appBackgroundColor
        )
    }
}

fun Color.isDark(): Boolean = this.luminance() < 0.5f

fun configureTransparentStatusBarForBackground(
    window: Window,
    view: View,
    appBackgroundColor: Color
) {
    try {
        val isAppBackgroundDark = appBackgroundColor.isDark()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Set transparent status bar
        @Suppress("DEPRECATION")
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        @Suppress("DEPRECATION")
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        // Modern approach for API 30+
        val windowInsetsController = WindowCompat.getInsetsController(window, view)
        // If app background (showing through transparent status bar) is dark, use light icons
        // If app background is light, use dark icons
        windowInsetsController.isAppearanceLightStatusBars = !isAppBackgroundDark
        windowInsetsController.isAppearanceLightNavigationBars = !isAppBackgroundDark

        // Legacy approach for older devices
        @Suppress("DEPRECATION")
        val systemUiVisibility = if (isAppBackgroundDark) {
            // Dark app background showing through transparent status bar - use white icons
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        } else {
            // Light app background showing through transparent status bar - use dark icons
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }

        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = systemUiVisibility

        // Force enable drawing system bar backgrounds
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    } catch (e: Exception) {
        Timber.tag("configureTransparentStatusBarForBackground").d("${e.message}")
    }
}

data class OnBoardingPageData(
    val title: String,
    val description: String,
    val imageResource: Int,
    val backgroundColor: Color
)

val pages = listOf(
    OnBoardingPageData(
        title = "Smart Home",
        description = "Smart Home can change way you live in the future",
        imageResource = R.drawable.on_boarding_image_four,
        backgroundColor = Color(0xFF1D2339)
    ),
    OnBoardingPageData(
        title = "Smart Control",
        description = "Monitor and control your home devices from anywhere in the world",
        imageResource = R.drawable.on_boarding_image_four,
        backgroundColor = Color(0xFFFBF0CB)
    ),
    OnBoardingPageData(
        title = "Energy Efficient",
        description = "Smart automation helps you save energy and reduce electricity bills",
        imageResource = R.drawable.on_boarding_image_four,
        backgroundColor = Color(0xFF1D2339)
    )
)