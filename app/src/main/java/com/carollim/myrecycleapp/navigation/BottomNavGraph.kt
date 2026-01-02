package com.carollim.myrecycleapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.carollim.myrecycleapp.presentation.home.HomeScreen
import com.carollim.myrecycleapp.presentation.auth.LoginScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.History.route) {
            // Placeholder for History Screen
            LoginScreen(navController = navController) // Using LoginScreen as a placeholder
        }
        composable(route = Screen.Recognize.route) {
            // Placeholder for Recognize Screen
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Catalog.route) {
            // Placeholder for Catalog Screen
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Profile.route) {
            // Placeholder for Profile Screen
            LoginScreen(navController = navController)
        }
    }
}