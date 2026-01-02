package com.carollim.myrecycleapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.carollim.myrecycleapp.presentation.auth.LoginScreen
import com.carollim.myrecycleapp.presentation.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        // Add other placeholder screens
        composable(Screen.History.route) {
            Text("History Screen")
        }
        composable(Screen.Catalog.route) {
            Text("Catalog Screen")
        }
        composable(Screen.Recognize.route) {
            Text("Recognize Screen")
        }
        composable(Screen.Profile.route) {
            Text("Profile Screen")
        }
    }
}
