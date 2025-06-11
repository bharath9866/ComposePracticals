package com.example.adaptivestreamingplayer.smartHome.lamp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.smartHome.lamp.widgets.COLOR_LIST
import com.example.adaptivestreamingplayer.smartHome.lamp.widgets.ColorItem
import com.example.adaptivestreamingplayer.smartHome.lamp.widgets.CustomSeekBar
import com.example.adaptivestreamingplayer.smartHome.lamp.widgets.LightBeamCanvas
import com.example.adaptivestreamingplayer.smartHome.lamp.widgets.RealisticRopeLightSwitch
import com.example.adaptivestreamingplayer.ui.theme.getProductSansFont
import kotlin.math.roundToInt

@Composable
fun LightLampScreen(modifier: Modifier = Modifier) {

    var selectedColorIndex by remember { mutableIntStateOf(2) }
    var progress by remember { mutableFloatStateOf(0.6f) }
    var isLightOn by remember { mutableStateOf(true) }

    val animateBeamColor by animateColorAsState(
        targetValue = if (isLightOn) COLOR_LIST[selectedColorIndex] else Color.Red,
        label = ""
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(400.dp)
        ) {

            LightBeamCanvas(
                modifier = Modifier
                    .padding(start = 50.dp)
                    .padding(top = 230.dp)
                    .size(
                        width = 200.dp,
                        height = 300.dp
                    ),
                isVisible = isLightOn,
                lightOpacity = progress,
                topConeWidth = 86.dp,
                lightColor = animateBeamColor
            )

            // Red Lamp
            Image(
                painter = painterResource(R.drawable.img_red_light),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 50.dp)
                    .size(
                        height = 250.dp,
                        width = 200.dp
                    )
            )


            LightBeamCanvas(
                modifier = Modifier
                    .padding(top = 180.dp)
                    .offset(x = (-25).dp)
                    .size(
                        width = 200.dp,
                        height = 300.dp
                    ),
                isVisible = isLightOn,
                lightOpacity = progress,
                topConeWidth = 106.dp,
                lightColor = animateBeamColor
            )

            // Black Lamp
            Image(
                painter = painterResource(R.drawable.img_black_light),
                contentDescription = null,
                modifier = Modifier
                    .size(
                        height = 200.dp,
                        width = 150.dp
                    )
            )

            RealisticRopeLightSwitch(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .width(100.dp)
                    .height(400.dp),
                ropeColor = Color.Gray,
                handleColor = Color.Black,
                onLightSwitch = { isOn ->
                    isLightOn = isOn
                }
            )

            //Box(
            //    modifier = Modifier
            //        .fillMaxWidth()
            //        .height(70.dp)
            //        .background(
            //            brush = Brush.verticalGradient(
            //                colors = listOf(
            //                    Color.White,
            //                    Color.White,
            //                    Color.Transparent
            //                )
            //            )
            //        )
            //)
        }


        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Text(
                text = "Schedule",
                fontFamily = getProductSansFont(),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 28.sp
            )

            Text(
                text = "Form",
                fontFamily = getProductSansFont(),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 20.dp)
            )

            val scheduleText = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                ) {
                    append("06:00")
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                    )
                ) {
                    append(" PM")
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                    )
                ) {
                    append("    To    ")
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                ) {
                    append("11:00")
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                    )
                ) {
                    append(" PM")
                }

            }

            Text(
                text = scheduleText,
                fontFamily = getProductSansFont(),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(top = 10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Brightness",
                    fontFamily = getProductSansFont(),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier
                )

                Text(
                    text = "${(progress * 100).roundToInt()}%",
                    fontFamily = getProductSansFont(),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier
                )
            }





            CustomSeekBar(
                progress = progress,
                onProgressChanged = { progress = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Text(
                text = "Color of Lights",
                fontFamily = getProductSansFont(),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 21.sp,
                modifier = Modifier.padding(top = 10.dp)
            )


            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(COLOR_LIST.size) { index ->
                    ColorItem(
                        color = COLOR_LIST[index],
                        isSelected = selectedColorIndex == index,
                        onClick = {
                            selectedColorIndex = if (selectedColorIndex == index) -1 else index
                        }
                    )
                }
            }

        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp")
@Composable
fun LightScreenPreview() {
    LightLampScreen()
}