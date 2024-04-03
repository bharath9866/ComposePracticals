package com.example.adaptivestreamingplayer.orderApp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.orderApp.modal.Recommended
import com.example.adaptivestreamingplayer.orderApp.modal.YourMind
import com.example.adaptivestreamingplayer.orderApp.modal.recommendations
import com.example.adaptivestreamingplayer.orderApp.modal.yourMind

@Composable
fun Body(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        WhatsOnYourMind(modifier = Modifier, yourMind = yourMind)
        Recommendations(recommendations = recommendations)
    }
}

@Composable
private fun WhatsOnYourMind(modifier: Modifier = Modifier, yourMind: ArrayList<YourMind>) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "What's on your mind?",
            color = Color(0xFF32328c),
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 2.dp, end = 8.dp),
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(yourMind) {
                YourMindItem(it)
            }
        }
    }
}
@Composable
private fun YourMindItem(yourMind: YourMind) {

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(contentColor = Color.Black)
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFfdfcff))
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                modifier = Modifier
                    .padding(2.dp)
                    .size(30.dp),
                painter = painterResource(id = yourMind.image),
                contentDescription = ""
            )
            Text(
                text = yourMind.text,
                color = Color(0xFF32328c),
                fontSize = 11.sp,
                modifier = Modifier.padding(start = 2.dp, end = 8.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }

}
@Composable
private fun Recommendations(recommendations:ArrayList<Recommended>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Recommendations",
                color = Color(0xFF32328c),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 2.dp, end = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Show all",
                color = Color(0xFF32328c),
                fontSize = 10.sp,
                modifier = Modifier.padding(start = 2.dp, end = 8.dp),
                fontWeight = FontWeight.Bold
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recommendations) {
                RecommendedCard(it)
            }
        }
    }
}

@Composable
private fun RecommendedCard(recommended: Recommended) {
    Card(
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF2c308b))
                .width(150.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = recommended.image),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 6.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                )
                Rating(modifier = Modifier.align(Alignment.BottomCenter))
            }
            Text(
                text = recommended.dishName,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier,
                fontWeight = FontWeight.Bold
            )
            Text(text = recommended.timer)
        }
    }
}

@Composable
private fun Rating(
    modifier: Modifier = Modifier,
    text:String = "4.2"
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFe48e3d))
            .padding(vertical = 2.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Image(
            modifier = Modifier.size(6.dp),
            painter = painterResource(id = R.drawable.ic_feedback_start),
            contentDescription = ""
        )
        Text(
            text = text,
            color = Color.White,
            fontSize = 8.sp,
            modifier = Modifier,
            fontWeight = FontWeight.Bold
        )
    }
}