package com.carollim.myrecycleapp.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    // Add other main screens as needed
    object History : Screen("history")
    object Catalog : Screen("catalog")
    object Recognize : Screen("recognize")
    object Profile : Screen("profile")
}