package com.example.adaptivestreamingplayer.slThree

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.ui.theme.appFontFamily
import com.example.adaptivestreamingplayer.utils.PreviewDesktop
import com.example.adaptivestreamingplayer.utils.shimmerEffect

@Composable
fun CreatePlanCard(
    createPlanViewModel: CreatePlanViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        createPlanViewModel.callCardData()
    }
    val cardData by createPlanViewModel.cardData.collectAsStateWithLifecycle()
    if (cardData != null)
        CreatePlanCard(
            modifier = Modifier,
            cardData = cardData,
            onClickIncrement = { createPlanViewModel.incrementValue(it) }
        )
    else CreatePlanCardShimmer()
}

@Composable
fun CreatePlanCard(modifier: Modifier = Modifier, cardData: CreatePlan?, onClickIncrement: (Int) -> Unit = {}) {
    Column(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFFFF3CB),
                        Color(0xFFFFFFFF)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Hello ${cardData?.studentName?:""}",
            modifier = Modifier.padding(bottom = 4.dp),
            fontFamily = appFontFamily,
            fontSize = 16.sp,
            color = Color(0xFF14142B),
            fontWeight = FontWeight.SemiBold,
            lineHeight = 24.sp
        )
        Text(
            text = cardData?.descriptionOne?:"",
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = appFontFamily,
            fontSize = 11.sp,
            color = Color(0xFF14142B),
            fontWeight = FontWeight.Medium,
            lineHeight = 14.sp,
            letterSpacing = 0.1.sp
        )
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .padding(bottom = 2.dp)
                .background(Color(0xFFFFE29C))
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.plan_logo),
                modifier = Modifier
                    .size(width = 130.dp, height = 116.dp)
                    .aspectRatio(1f / 1f),
                contentDescription = "Plan Logo"
            )
            Column(
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Text(
                    text = cardData?.cardTitle?:"",
                    fontFamily = appFontFamily,
                    fontSize = 16.sp,
                    color = Color(0xFF14142B),
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 18.sp,
                    letterSpacing = 0.25.sp
                )
                Text(
                    text = cardData?.cardDescription?:"",
                    modifier = Modifier.padding(bottom = 14.dp),
                    fontFamily = appFontFamily,
                    fontSize = 12.sp,
                    color = Color(0xFF14142B),
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp,
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFFFBB040),
                                    Color(0xFFF7941D),
                                    Color(0xFFF15A29)
                                )
                            )
                        )
                        .clickable {
                            onClickIncrement(cardData?.incrementValue ?: 0)
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    Text(
                        text = "Created Plan Value ${cardData?.incrementValue?:0}",
                        fontFamily = appFontFamily,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 24.sp
                    )
                }
            }
        }

    }
}

@Composable
fun CreatePlanCardShimmer() {
    Column(
        modifier = Modifier
            .background(Color(0xFFFCFCFC))
            .padding(16.dp)
    ) {
        Box(modifier = Modifier
            .padding(bottom = 4.dp)
            .height(24.dp)
            .fillMaxWidth(0.4f)
            .shimmerEffect())
        Box(modifier = Modifier
            .padding(bottom = 16.dp)
            .height(24.dp)
            .fillMaxWidth(0.5f)
            .shimmerEffect())
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .padding(bottom = 2.dp)
                .background(Color(0xFFFCFCFC))
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 130.dp, height = 116.dp)
                    .aspectRatio(1f / 1f)
                    .shimmerEffect()
            )
            Column(
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Box(modifier = Modifier
                    .padding(bottom = 4.dp)
                    .height(24.dp)
                    .fillMaxWidth(0.5f)
                    .shimmerEffect())
                Box(modifier = Modifier
                    .padding(bottom = 14.dp)
                    .height(24.dp)
                    .fillMaxWidth(0.8f)
                    .shimmerEffect())
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(40.dp))
                    .height(40.dp)
                    .fillMaxWidth(0.4f)
                    .shimmerEffect()
                    .padding(bottom = 14.dp)
                )
            }
        }

    }
}

@PreviewDesktop
@Composable
private fun CreatePlanCardPreview() {
    CreatePlanCard(modifier = Modifier, cardData = createPlan)
}