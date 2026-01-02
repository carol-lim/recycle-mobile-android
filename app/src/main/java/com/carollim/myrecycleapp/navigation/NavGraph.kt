package com.carollim.myrecycleapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.carollim.myrecycleapp.presentation.auth.LoginScreen
import com.carollim.myrecycleapp.presentation.main.MainScreen

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
            // The Home route now leads to the MainScreen which contains the BottomNavGraph
            MainScreen()
        }
    }
}
