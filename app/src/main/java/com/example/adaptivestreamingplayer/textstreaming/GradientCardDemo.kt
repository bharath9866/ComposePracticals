package com.example.adaptivestreamingplayer.textstreaming

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = false, showBackground = true, backgroundColor = 0xFF000000)
fun GradientCardDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
//        TopAppBar(
//            title = {
//                Text(
//                    text = "Gradient Card Demo",
//                    fontWeight = FontWeight.Bold
//                )
//            },
//            colors = TopAppBarDefaults.topAppBarColors(
//                containerColor = MaterialTheme.colorScheme.primary,
//                titleContentColor = MaterialTheme.colorScheme.onPrimary
//            )
//        )

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Original gradient card from XML
//            Text(
//                text = "Original XML Design",
//                style = MaterialTheme.typography.headlineSmall,
//                fontWeight = FontWeight.Bold
//            )

            val cornerRadius = 16.dp
            val strokeWidth = 2.dp
            val boxSize = 100.dp

            // Calculate scaled offsets (from 47x47 vector space to boxSize)
            val scale = with(LocalDensity.current) { boxSize.toPx() / 47f }

            Box(
                modifier = Modifier
                    .width(boxSize)
                    .aspectRatio(0.7664f)
                    .drawBehind {
                        // Stroke Gradient
                        drawRoundRect(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.White, Color(0xFF39C7FF)),
                                start = Offset(5.11f * scale, 5.8f * scale),
                                end = Offset(40.6f * scale, 43.74f * scale)
                            ),
                            size = this.size,
                            cornerRadius = CornerRadius(cornerRadius.toPx()),
                            style = Stroke(width = strokeWidth.toPx())
                        )

                        // Fill Gradient
                        drawRoundRect(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF39C7FF), Color(0xFF39C7FF)),
                                start = Offset(4.51f * scale, 42.52f * scale),
                                end = Offset(42.17f * scale, 4.86f * scale)
                            ),
                            size = this.size,
                            cornerRadius = CornerRadius(cornerRadius.toPx())
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Card\nGradient",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }

            GradientCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                cornerRadius = 12.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Original Gradient Card",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            // Custom gradient variations
//            Text(
//                text = "Custom Variations",
//                style = MaterialTheme.typography.headlineSmall,
//                fontWeight = FontWeight.Bold
//            )

            // Purple gradient
            CustomGradientCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                backgroundColors = listOf(Color(0xFF6B73FF), Color(0xFF9B59B6)),
                borderColors = listOf(Color.White, Color(0xFF6B73FF))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Purple Gradient",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Green gradient
            CustomGradientCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                backgroundColors = listOf(Color(0xFF2ECC71), Color(0xFF27AE60)),
                borderColors = listOf(Color.White, Color(0xFF2ECC71)),
                cornerRadius = 20.dp,
                borderWidth = 8.dp,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Green Gradient",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Orange gradient with different border
            CustomGradientCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                backgroundColors = listOf(Color(0xFFFF6B35), Color(0xFFFF8E53)),
                borderColors = listOf(Color(0xFFFFE5B4), Color(0xFFFF6B35)),
                borderWidth = 8.dp,
                cornerRadius = 20.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Orange Gradient",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Usage in a card layout
//            Text(
//                text = "Card Layout Example",
//                style = MaterialTheme.typography.headlineSmall,
//                fontWeight = FontWeight.Bold
//            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GradientCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(140.dp),
                    cornerRadius = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Card 1",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Content here",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                    }
                }

                GradientCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(140.dp),
                    cornerRadius = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Card 2",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "More content",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}