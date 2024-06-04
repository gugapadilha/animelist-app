package com.example.myanimelist.nav

sealed class Screen(val route: String) {

    object Home: Screen(route = "home_screen")
    object Watched: Screen(route = "watched_screen")
    object Favorite: Screen(route = "favorite_screen")
    object Best: Screen(route = "best_screen")
}