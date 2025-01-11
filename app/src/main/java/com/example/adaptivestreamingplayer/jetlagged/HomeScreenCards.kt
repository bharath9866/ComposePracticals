package com.example.adaptivestreamingplayer.jetlagged


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.adaptivestreamingplayer.ui.theme.HeadingStyle
import com.example.adaptivestreamingplayer.ui.theme.JetLaggedTheme

@Composable
fun BasicInformationCard(
    modifier: Modifier = Modifier,
    borderColor: Color,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(24.dp)

    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            contentColor = JetLaggedTheme.extraColors.cardBackground,
            containerColor = Color.White
        ),
        modifier = modifier.padding(8.dp),
        border = BorderStroke(2.dp, borderColor)
    ) {
        Box {
            content()
        }
    }
}

@Composable
fun HomeScreenCardHeading(text: String) {
    Text(
        text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        textAlign = TextAlign.Center,
        style = HeadingStyle
    )
}
