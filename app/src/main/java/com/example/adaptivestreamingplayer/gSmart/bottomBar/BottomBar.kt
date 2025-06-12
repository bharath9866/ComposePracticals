package com.example.adaptivestreamingplayer.gSmart.bottomBar

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BedroomChild
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.ChildFriendly
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BedroomChild
import androidx.compose.material.icons.outlined.ChildCare
import androidx.compose.material.icons.outlined.ChildFriendly
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@Composable
fun BottomBarScreen(modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavigationItem(
            title = "Child",
            selectedIcon = Icons.Filled.ChildCare,
            unselectedIcon = Icons.Outlined.ChildCare,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            hasNews = true,
        ),
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(1)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                // navController.navigate(item.title)
                            },
                            label = {
                                Text(text = item.title)
                            },
                            alwaysShowLabel = false,
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if(item.badgeCount != null) {
                                            Badge {
                                                Text(text = item.badgeCount.toString())
                                            }
                                        } else if(item.hasNews) {
                                            Badge()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            }
                        )
                    }
                }
            }) {
            when(items[selectedItemIndex].title) {
                "Child" -> {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            items[selectedItemIndex].title,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                "Home" -> {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            items[selectedItemIndex].title,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                "Settings" -> {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            items[selectedItemIndex].title,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BottomBarScreenPreview() {
    BottomBarScreen()
}