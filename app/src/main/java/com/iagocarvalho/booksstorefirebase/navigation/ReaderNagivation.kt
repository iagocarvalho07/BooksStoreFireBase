package com.iagocarvalho.booksstorefirebase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iagocarvalho.booksstorefirebase.screens.ReaderSplashScreen
import com.iagocarvalho.booksstorefirebase.screens.details.BookDetailsScreen
import com.iagocarvalho.booksstorefirebase.screens.home.HomeScreen
import com.iagocarvalho.booksstorefirebase.screens.login.LoginScreen
import com.iagocarvalho.booksstorefirebase.screens.search.ReaderBookSearchScreen
import com.iagocarvalho.booksstorefirebase.screens.stats.ReaderStatsScreen
import com.iagocarvalho.booksstorefirebase.screens.update.BookUpadateScreen

@Composable
fun ReaderNagivation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name){
        composable(ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name){
            HomeScreen(navController = navController)
        }
        composable(ReaderScreens.SearchScreen.name){
            ReaderBookSearchScreen(navController = navController)
        }
        composable(ReaderScreens.DetailsScreens.name){
            BookDetailsScreen(navController = navController)
        }
        composable(ReaderScreens.UpadateScreen.name){
            BookUpadateScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name){
            ReaderStatsScreen(navController = navController)
        }
    }
}