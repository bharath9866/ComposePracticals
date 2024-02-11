package com.example.adaptivestreamingplayer.ilts.report

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.adaptivestreamingplayer.ui.theme.appFontFamily

@Preview
@Composable
fun PracticeAceText(onClick:() -> Unit = {}) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text ="Practice to Ace Tests \uD83C\uDF1F",
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            fontWeight = FontWeight.SemiBold,
            fontFamily = appFontFamily,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text ="Cheers to achievements! Create CYOT to expand your knowledge! \uD83D\uDE80\uD83C\uDF10",
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            fontWeight = FontWeight.SemiBold,
            fontFamily = appFontFamily,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(56.dp))
                .background(Color(0xFF324452)).clickable { onClick() }
        ) {
            Text(
                text ="TAKE CYOT",
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                fontWeight = FontWeight.SemiBold,
                fontFamily = appFontFamily
            )
        }
    }
}