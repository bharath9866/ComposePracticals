package com.example.adaptivestreamingplayer.gSmart.sideMenu

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.canvas.SpeechBubbleShape
import com.example.adaptivestreamingplayer.ui.theme.getProductSansFont

@Composable
fun SideMenuRoute() {
    SideMenuScreen()
}

@Composable
fun SideDrawer(modifier: Modifier = Modifier) {
    
}

@Composable
fun SideMenuScreen() {
    val gradientColors = listOf(
        Color(0xFF017DC0),
        Color(0xFF00B5E8),
        Color(0xFFDB4CCD),
        Color(0xFFE80000)
    )

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(Color(0xFF0E1417)) // Dark background color,
    ) {
        // Gradient overlays
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(59.dp)
                .background(
                    Brush.horizontalGradient(gradientColors)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(59.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0xFF0E1417)),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(59.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.horizontalGradient(gradientColors)
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(59.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF0E1417), Color.Transparent),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.male_default_profile),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFA500))
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = "Leo Nard",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "9123456789",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = "abc@gmail.com",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Column(
                modifier = Modifier.padding(top = 23.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                MenuItem(id = R.drawable.auto_mation_sidebar, text = "Automation")
                MenuItem(id = R.drawable.rgb_sidebar, text = "RGB")
                MenuItem(id = R.drawable.add_child_sidebar, text = "Add Child")
                MenuItem(id = R.drawable.about_sidebar, text = "About")
                MenuItem(id = R.drawable.settings_sidebar, text = "Settings")
                MenuItem(id = R.drawable.notifications_sidebar, text = "Notifications")
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Version: 1.40",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun MenuItem(@DrawableRes id: Int, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = id),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color.White,
            fontFamily = getProductSansFont(),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

@Preview
@Composable
fun SideMenuScreenPreview() {
    SideMenuScreen()
}

class SemiArcShape(
    val arcHeight: Dp = 51.dp, // Controls the height/depth of the arc
    val cornerRadius: Dp = 10.dp // Rounded corners at the ends
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val arcHeightPx = with(density) { arcHeight.toPx() }
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }

        val path = Path().apply {
            // Start at the bottom-left corner (with rounded corner adjustment)
            moveTo(x = 0f, y = 0f)

            // Line to the start of the bottom-left rounded corner
            lineTo(x = cornerRadiusPx, y = 0f)

            // Bottom-left rounded corner
            quadraticTo(x1 = 0f, y1 = 0f, x2 = 0f, y2 = cornerRadiusPx)

            // Line down to the start of the arc
            lineTo(x = 0f, y = size.height - arcHeightPx)

            // Draw the semi-arc (curving downward)
            quadraticTo(
                x1 = size.width / 2f,
                y1 = size.height + arcHeightPx,
                x2 = size.width,
                y2 = size.height - arcHeightPx
            )

            // Line up to the bottom-right rounded corner
            lineTo(x = size.width, y = cornerRadiusPx)

            // Bottom-right rounded corner
            quadraticTo(x1 = size.width, y1 = 0f, x2 = size.width - cornerRadiusPx, y2 = 0f)

            // Line back to the starting point
            lineTo(x = 0f, y = 0f)

            close()
        }
        return Outline.Generic(path)
    }
}