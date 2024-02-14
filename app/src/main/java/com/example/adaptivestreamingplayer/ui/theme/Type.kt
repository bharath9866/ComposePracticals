package com.example.adaptivestreamingplayer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R

val appFontFamily = FontFamily(
    Font(R.font.montserrat_black, FontWeight.Black),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
)



private val defaultTypography = Typography()
val appTypography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = appFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = appFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = appFontFamily),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = appFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = appFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = appFontFamily),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = appFontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = appFontFamily),
    titleSmall = defaultTypography.bodyLarge.copy(fontFamily = appFontFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = appFontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = appFontFamily),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = appFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = appFontFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = appFontFamily)
)

val TitleBarStyle = TextStyle(
    fontSize = 22.sp,
    fontWeight = FontWeight(700),
    letterSpacing = 0.5.sp,
    fontFamily = appFontFamily
)

val HeadingStyle = TextStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight(600),
    letterSpacing = 0.5.sp,
    fontFamily = appFontFamily
)

val SmallHeadingStyle = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight(600),
    letterSpacing = 0.5.sp,
    fontFamily = appFontFamily
)

val LegendHeadingStyle = TextStyle(
    fontSize = 10.sp,
    fontWeight = FontWeight(600),
    letterSpacing = 0.5.sp,
    fontFamily = appFontFamily
)
