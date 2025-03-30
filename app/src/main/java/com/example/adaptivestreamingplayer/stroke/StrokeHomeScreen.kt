package com.example.adaptivestreamingplayer.stroke

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.adaptivestreamingplayer.core.AppRoute

@Composable
fun StrokeHomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.navigate(AppRoute.StrokeTextRoute)
            }
        ) {
            Text("StrokeText")
        }
        Button(
            onClick = {
                navController.navigate(AppRoute.StrokeBrushRoute)
            }
        ) {
            Text("StrokeBrush")
        }
    }
}