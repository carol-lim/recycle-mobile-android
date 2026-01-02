package com.carollim.myrecycleapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.carollim.myrecycleapp.presentation.home.HomeScreen
import com.carollim.myrecycleapp.presentation.placeholder.PlaceholderScreen

@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.History.route) {
            PlaceholderScreen(screenName = "History")
        }
        composable(route = Screen.Recognize.route) {
            PlaceholderScreen(screenName = "Recognize")
        }
        composable(route = Screen.Catalog.route) {
            PlaceholderScreen(screenName = "Catalog")
        }
        composable(route = Screen.Profile.route) {
            PlaceholderScreen(screenName = "Profile")
        }
    }
}
