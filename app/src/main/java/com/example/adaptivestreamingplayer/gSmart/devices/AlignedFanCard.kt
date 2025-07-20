package com.example.adaptivestreamingplayer.gSmart.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R

@Composable
fun AlignedFanCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(280.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF00BCD4), // Cyan
                            Color(0xFF2196F3)  // Blue
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1f, 1f)
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Fan Icon Container - Perfectly Centered
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .background(
                            Color.White.copy(alpha = 0.2f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fan),
                        contentDescription = "Fan Icon",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }

                // Spacer for proper vertical spacing
                Spacer(modifier = Modifier.height(32.dp))

                // Fan Title - Centered
                Text(
                    text = "Fan",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Light,
                        fontSize = 36.sp
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                // Spacer for proper vertical spacing
                Spacer(modifier = Modifier.height(24.dp))

                // Speed Text - Centered
                Text(
                    text = "Speed 4",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Light,
                        fontSize = 24.sp
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// Alternative with exact pixel-perfect alignment
@Composable
fun PixelPerfectFanCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(280.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF00BCD4),
                            Color(0xFF2196F3)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1f, 1f)
                    )
                )
        ) {
            // Fan Icon - Top centered with specific positioning
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(
                        Color.White.copy(alpha = 0.2f),
                        CircleShape
                    )
                    .align(Alignment.TopCenter)
                    .offset(y = 48.dp)
                ,
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_fan),
                    contentDescription = "Fan Icon",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            // Fan Title - Center positioned
            Text(
                text = "Fan",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Light,
                    fontSize = 36.sp
                ),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 8.dp)
            )

            // Speed Text - Bottom centered with specific positioning
            Text(
                text = "Speed 4",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Light,
                    fontSize = 24.sp
                ),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-48).dp)
            )
        }
    }
}

// Usage in your activity/fragment
@Preview
@Composable
fun FanScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AlignedFanCard()
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // You can also use the pixel-perfect version
        PixelPerfectFanCard()
    }
}