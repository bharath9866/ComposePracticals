package com.example.adaptivestreamingplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.example.adaptivestreamingplayer.core.HomeRoute
import com.example.adaptivestreamingplayer.core.NavigationOne
import com.example.adaptivestreamingplayer.core.NavigationTwo

@Composable
fun NavigationOneScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry
) {
    val args by remember {
        mutableStateOf(navBackStackEntry.toRoute<NavigationOne>())
    }
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("id: ${args.id ?: 0}")
        Text("name: ${args.name ?: ""}")
        Button(onClick = {
            navController.navigate(NavigationTwo(2, "NavigationTwoScreen"))
        }) {
            Text("Navigate to NavTwo")
        }
    }
}


@Composable
fun NavigationTwoScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry
) {
    val args by remember {
        mutableStateOf(navBackStackEntry.toRoute<NavigationOne>())
    }
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("id: ${args.id ?: 0}")
        Text("name: ${args.name ?: ""}")
        Button(onClick = {
            navController.navigate(HomeRoute)
        }) {
            Text("NavBack To Home")
        }
    }
}