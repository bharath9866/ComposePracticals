package com.example.adaptivestreamingplayer.vernacular

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.utils.ImageVector
import com.example.adaptivestreamingplayer.utils.onClickWithoutRipple
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*


@Preview
@Composable
fun VernacularMain() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("English", "Hindi", "Hinglish", "Telugu", "Tamil")
    val itemsLanguage = arrayListOf(
        Language(1, "English"),
        Language(2, "Hindi"),
        Language(3, "Hinglish"),
        Language(4, "Telugu"),
        Language(5, "Tamil")
    )
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Vernacular()
        CustomDropdownMenu(
            list = items,
            defaultSelected = items[selectedIndex],
            color = Color.Cyan,
            modifier = Modifier,
            onSelected = {
                selectedIndex = it
            }
        )

        DropdownList(
            items = itemsLanguage,
            onSelectedLanguageId = {
                selectedIndex = it
            }
        )
    }
}

@Composable
fun CustomDropdownMenu(
    list: List<String>, // Menu Options
    defaultSelected: String, // Default Selected Option on load
    color: Color, // Color
    modifier: Modifier, //
    onSelected: (Int) -> Unit, // Pass the Selected Option
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    var expand by remember { mutableStateOf(false) }
    var stroke by remember { mutableIntStateOf(1) }
    Box(
        modifier
            .padding(30.dp)
            .border(
                border = BorderStroke(stroke.dp, color),
                shape = RoundedArrowShape(
                    cornerRadius = 39.dp,
                    arrowWidth = 16.dp,
                    arrowHeight = 16.dp,
                    ArrowPosition.TopRight
                )
            )
            .background(
                color = Color.Black, shape = RoundedArrowShape(
                    cornerRadius = 39.dp,
                    arrowWidth = 16.dp,
                    arrowHeight = 16.dp,
                    ArrowPosition.TopRight
                )
            )
            .clickable {
                expand = true
                stroke = if (expand) 2 else 1
            },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = defaultSelected,
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(16.dp)
        )

        DropdownMenu(
            expanded = expand,
            onDismissRequest = {
                expand = false
                stroke = if (expand) 2 else 1
            },
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
            modifier = Modifier
                .background(Color.White)
                .padding(2.dp)
                .fillMaxWidth(.4f)
        ) {
            list.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier,
                    contentPadding = PaddingValues(0.dp, 0.dp),
                    text = {
                        Text(
                            text = item,
                            color = color,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    onClick = {
                        selectedIndex = index
                        expand = false
                        stroke = if (expand) 2 else 1
                        onSelected(selectedIndex)
                    }
                )
            }
        }

    }
}

@Preview
@Composable
fun Vernacular(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("English", "Hindi", "Hinglish")
    var selectedIndex by remember { mutableIntStateOf(0) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ImageVector(
            modifier = Modifier.size(24.dp),
            imageModifier = Modifier,
            imageVector = R.drawable.ic_language_icon,
            contentAlignment = Alignment.Center
        )
        Column {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(39.dp))
                    .onClickWithoutRipple { expanded = true }
                    .border(1.dp, color = Color(0xFFBE5E22), shape = RoundedCornerShape(39.dp))
                    .padding(vertical = 4.5.dp)
                    .padding(start = 7.5.dp, end = 3.5.dp)
                ,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Text(
                    text = items[selectedIndex],
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF9A4C1C)
                )
                ImageVector(
                    modifier = Modifier.size(24.dp),
                    imageModifier = Modifier,
                    imageVector = R.drawable.ic_dropdown_icon,
                )
            }
            MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .wrapContentSize()
                        .border(1.dp, color = Color(0xFFBE5E22))
                        .background(Color(0xFFFFAD7A)),
                ) {
                    items.forEachIndexed { index, s ->
                        Text(
                            text = items[index],
                            modifier = Modifier
                                .padding(
                                    top = 4.5.dp,
                                    bottom = 4.5.dp,
                                    start = 7.5.dp,
                                    end = 24.5.dp
                                )
                                .clickable {
                                    selectedIndex = index
                                    expanded = false
                                },
                        )
                    }
                }
            }
        }
    }

}

class RoundedArrowShape(
    private val cornerRadius: Dp,
    private val arrowWidth: Dp,
    private val arrowHeight: Dp,
    private val arrowPosition: ArrowPosition // Enum for arrow position
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        // Use density to convert Dp to Px
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }
        val arrowWidthPx = with(density) { arrowWidth.toPx() }
        val arrowHeightPx = with(density) { arrowHeight.toPx() }

        return Outline.Generic(Path().apply {
            reset()

            // Start drawing the rounded rectangle
            moveTo(cornerRadiusPx, 0f)
            lineTo(size.width - cornerRadiusPx, 0f)
            arcTo(
                rect = Rect(
                    offset = Offset(size.width - cornerRadiusPx, 0f),
                    size = Size(cornerRadiusPx, cornerRadiusPx)
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(size.width, size.height - cornerRadiusPx)
            arcTo(
                rect = Rect(
                    offset = Offset(size.width - cornerRadiusPx, size.height - cornerRadiusPx),
                    size = Size(cornerRadiusPx, cornerRadiusPx)
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(cornerRadiusPx, size.height)
            arcTo(
                rect = Rect(
                    offset = Offset(0f, size.height - cornerRadiusPx),
                    size = Size(cornerRadiusPx, cornerRadiusPx)
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(0f, cornerRadiusPx)
            arcTo(
                rect = Rect(
                    offset = Offset(0f, 0f),
                    size = Size(cornerRadiusPx, cornerRadiusPx)
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            // Draw the arrow based on its position
            when (arrowPosition) {
                ArrowPosition.TopRight -> {
                    lineTo((size.width - cornerRadiusPx) + arrowWidthPx, 0f)
                    lineTo((size.width - cornerRadiusPx) + arrowWidthPx/2, -arrowHeightPx/2)
                    lineTo(size.width - cornerRadiusPx, 0f)
                    lineTo((size.width - cornerRadiusPx) + arrowWidthPx, 0f)
                }
                // Add other positions as needed (e.g., BottomLeft, etc.)
                ArrowPosition.BottomLeft -> {}
            }

            close()
        })
    }
}

// Enum for arrow positions
enum class ArrowPosition {
    TopRight,
    BottomLeft,
    // Add other positions as needed
}

class RoundedArrowShapeTwo(
    private val cornerRadius: Dp,
    private val arrowWidth: Dp,
    private val arrowHeight: Dp,
    private val arrowAngle: Float // Angle of the arrow
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        // Use density to convert Dp to Px
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }
        val arrowWidthPx = with(density) { arrowWidth.toPx() }
        val arrowHeightPx = with(density) { arrowHeight.toPx() }

        // Compute arrow's rotation (manual)
        val arrowStartX = size.width - arrowWidthPx / 2
        val arrowStartY = 0f
        val arrowEndX = arrowStartX + arrowHeightPx * kotlin.math.cos(Math.toRadians(arrowAngle.toDouble())).toFloat()
        val arrowEndY = arrowHeightPx * kotlin.math.sin(Math.toRadians(arrowAngle.toDouble())).toFloat()

        return Outline.Generic(Path().apply {
            reset()

            // Start drawing the rounded rectangle
            moveTo(cornerRadiusPx, 0f)
            lineTo(size.width - arrowWidthPx, 0f)

            // Draw the arrow manually
            lineTo(arrowStartX, arrowStartY)
            lineTo(arrowEndX, arrowEndY)
            lineTo(arrowStartX + arrowWidthPx, arrowStartY)
            lineTo(size.width - arrowWidthPx, 0f)

            // Draw rounded corners
            arcTo(
                rect = Rect(
                    offset = Offset(size.width - cornerRadiusPx, 0f),
                    size = Size(cornerRadiusPx, cornerRadiusPx)
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            arcTo(
                rect = Rect(
                    offset = Offset(cornerRadiusPx, size.height - cornerRadiusPx),
                    size = Size(cornerRadiusPx, cornerRadiusPx)
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            arcTo(
                rect = Rect(
                    offset = Offset(0f, cornerRadiusPx),
                    size = Size(cornerRadiusPx, cornerRadiusPx)
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            arcTo(
                rect = Rect(
                    offset = Offset(size.width - cornerRadiusPx, size.height - cornerRadiusPx),
                    size = Size(cornerRadiusPx, cornerRadiusPx)
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            close()
        })
    }
}



