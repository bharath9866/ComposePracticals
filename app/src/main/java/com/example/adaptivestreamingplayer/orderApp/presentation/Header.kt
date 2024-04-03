package com.example.adaptivestreamingplayer.orderApp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun Header(
    modifier:Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        var value by remember { mutableStateOf(TextFieldValue("")) }
        BasicTextField(
            value = value,
            modifier = Modifier.weight(0.5f),
            onValueChange = { value = it },
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .border(width = 1.dp, Color(0xFFa8aab8), RoundedCornerShape(50.dp))
                        .background(Color.White, RoundedCornerShape(50.dp))
                        .weight(0.5f)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_order_search),
                        modifier = Modifier,
                        contentDescription = ""
                    )
                    if (value.text.isEmpty()) {
                        Text(
                            "Search for dish or ingredient",
                            color = Color(0xFF808080)
                        )
                    }

                    innerTextField()
                }
            },
        )
        ScheduledDish()
        Image(
            imageVector = Icons.Filled.NotificationsNone,
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
        Image(
            imageVector = Icons.Filled.PowerSettingsNew,
            contentDescription = "",
            modifier = Modifier.size(30.dp),
            colorFilter = ColorFilter.tint(Color.Red)
        )
    }
}
@Preview
@Composable
private fun ScheduledDish(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .height(50.dp)
            .background(Color(0xFF162a35))
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            modifier = Modifier
                .size(50.dp),
            painter = painterResource(id = R.drawable.ic_food_in_mind_default),
            contentDescription = ""
        )
        Column {
            Text(
                text = "Italian Spaghetti",
                color = Color(0xFFe5eff7),
                fontSize = 11.sp,
                modifier = Modifier.padding(start = 2.dp, end = 8.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Scheduled 6:30 AM",
                color = Color(0xFFe5eff7),
                fontSize = 11.sp,
                modifier = Modifier.padding(start = 2.dp, end = 8.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}