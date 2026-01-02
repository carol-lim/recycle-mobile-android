package com.carollim.myrecycleapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        route = Screen.Home.route,
        title = "Home",
        icon = Icons.Default.Home
    )
    object History : BottomNavItem(
        route = Screen.History.route,
        title = "History",
        icon = Icons.Default.History
    )
    object Catalog : BottomNavItem(
        route = Screen.Catalog.route,
        title = "Catalog",
        icon = Icons.Default.List
    )
    object Recognize : BottomNavItem(
        route = Screen.Recognize.route,
        title = "Recognize",
        icon = Icons.Default.Camera
    )
    object Profile : BottomNavItem(
        route = Screen.Profile.route,
        title = "Profile",
        icon = Icons.Default.AccountCircle
    )
}
