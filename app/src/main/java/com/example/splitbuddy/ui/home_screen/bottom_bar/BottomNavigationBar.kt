package com.example.splitbuddy.ui.home_screen.bottom_bar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController){

    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Groups,
        BottomNavItem.FriendList,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .padding(12.dp,0.dp)
            .navigationBarsPadding()
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, color = Color.Gray, RoundedCornerShape(16.dp))
            .height(50.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) {

        items.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {

                    navController.navigate(item.route) {

                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.route,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
}