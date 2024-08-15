package com.example.myanimelist.nav

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myanimelist.presentation.ui.SplashScreen
import com.example.myanimelist.presentation.ui.FavoriteScreen
import com.example.myanimelist.presentation.ui.HomeScreen
import com.example.myanimelist.presentation.ui.WatchedScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable(route = "splash_screen") {
            SplashScreen()
        }
        composable(route = "home_screen") {
            HomeScreen(navController = navController)
        }
        composable(route = "search_screen") {
            SearchScreen(navController = navController)
        }
        composable(route = "watched_screen") {
            WatchedScreen(navController = navController)
        }
        composable(route = "favorite_screen") {
            FavoriteScreen(navController = navController)
        }
    }
}