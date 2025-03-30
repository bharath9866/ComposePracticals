package com.example.adaptivestreamingplayer.stroke.strokeText

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke.Companion.DefaultCap
import androidx.compose.ui.graphics.drawscope.Stroke.Companion.DefaultJoin
import androidx.compose.ui.graphics.drawscope.Stroke.Companion.DefaultMiter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.utils.PreviewNexusSeven

@Composable
fun StrokeTextHome() {
    val strokeJoinList = listOf(StrokeJoin.Miter, StrokeJoin.Round, StrokeJoin.Bevel)
    val strokeCapList = listOf(StrokeCap.Butt, StrokeCap.Round, StrokeCap.Square)
    val miterList = listOf(1f, 2f, 3f, 4f)
    var selectedJoinStroke by remember { mutableStateOf(strokeJoinList[0]) }
    var selectedCapStroke by remember { mutableStateOf(strokeCapList[0]) }
    var selectedMiter by remember { mutableFloatStateOf(miterList[3]) }

    Column(
        modifier = Modifier.background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DropDownButton(
                strokeCapList = strokeCapList,
                selectedCapStroke = selectedCapStroke,
                onSelectedCap = {
                    selectedCapStroke = it
                },
            )
            DropDownButton(
                strokeJoinList = strokeJoinList,
                selectedJoinStroke = selectedJoinStroke,
                onSelectedJoin = {
                    selectedJoinStroke = it
                },
            )
            DropDownButton(
                miterList = miterList,
                selectedMiter = selectedMiter,
                onSelectedMiter = {
                    selectedMiter = it
                },
            )
        }
        StrokeTextContent(
            width = 10f,
            strokeJoin = selectedJoinStroke,
            strokeCap = selectedCapStroke,
            miter = selectedMiter
        )
    }
}

@Composable
private fun DropDownButton(
    strokeCapList: List<StrokeCap>? = null,
    strokeJoinList: List<StrokeJoin>? = null,
    miterList: List<Float>? = null,
    selectedJoinStroke: StrokeJoin = DefaultJoin,
    selectedCapStroke: StrokeCap = DefaultCap,
    selectedMiter: Float = DefaultMiter,
    onSelectedCap: (StrokeCap) -> Unit = {},
    onSelectedJoin: (StrokeJoin) -> Unit = {},
    onSelectedMiter: (Float) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf("") }
    LaunchedEffect(selectedJoinStroke, selectedCapStroke, selectedMiter) {
        if (!strokeCapList.isNullOrEmpty()) buttonText = "$selectedCapStroke Cap"
        if (!strokeJoinList.isNullOrEmpty()) buttonText = "$selectedJoinStroke Join"
        if (!miterList.isNullOrEmpty()) buttonText = "$selectedMiter Miter"
    }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { expanded = true })
            .background(Color.Gray)
            .padding(8.dp),
    ) {
        Text(
            text = buttonText,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                color = Color.Black,
                fontSize = 25.sp,
                drawStyle = Stroke(
                    width = 4f,
                    cap = selectedCapStroke,
                    join = selectedJoinStroke,
                    miter = selectedMiter
                )
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            strokeCapList?.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onSelectedCap(item)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = item.toString(),
                            style = TextStyle(
                                color = Color(0xFFF67C37),
                                fontSize = 75.sp,
                                drawStyle = Stroke(
                                    width = 7.5f,
                                    cap = item,
                                ),
                            )
                        )
                    }
                )
            }
            strokeJoinList?.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onSelectedJoin(item)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = item.toString(),
                            style = TextStyle(
                                color = Color(0xFFF67C37),
                                fontSize = 75.sp,
                                drawStyle = Stroke(
                                    width = 7.5f,
                                    join = item,
                                ),
                            )
                        )
                    }
                )
            }
            miterList?.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onSelectedMiter(item)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = item.toString(),
                            style = TextStyle(
                                color = Color(0xFFF67C37),
                                fontSize = 75.sp,
                                drawStyle = Stroke(
                                    width = 7.5f,
                                    miter = item,
                                    join = StrokeJoin.Miter
                                ),
                            )
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun StrokeTextContent(
    modifier: Modifier = Modifier,
    width: Float = 0f,
    miter: Float = DefaultMiter,
    strokeCap: StrokeCap = DefaultCap,
    strokeJoin: StrokeJoin = DefaultJoin,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            text = textContent,
            style = TextStyle(
                color = Color(0xFFF67C37),
                fontSize = 150.sp,
                drawStyle = Stroke(
                    width = width,
                    join = strokeJoin,
                    cap = strokeCap,
                    miter = miter
                ),
            )
        )
    }
}

@PreviewNexusSeven
@Composable
private fun StrokeTextPreview() {
    StrokeTextHome()
}

const val textContent = "- - < A B C D E F G H I J K L M N O P Q R S T U V W X Y Z,\na b c d e f g h i j k l m n o p q r s t u v w x y z,\n0 1 2 3 4 5 6 7 8 9"