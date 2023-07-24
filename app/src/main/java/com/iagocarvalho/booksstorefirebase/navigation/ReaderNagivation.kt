package com.iagocarvalho.booksstorefirebase.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iagocarvalho.booksstorefirebase.screens.ReaderSplashScreen
import com.iagocarvalho.booksstorefirebase.screens.details.BookDetailsScreen
import com.iagocarvalho.booksstorefirebase.screens.home.HomeScreen
import com.iagocarvalho.booksstorefirebase.screens.login.LoginScreen
import com.iagocarvalho.booksstorefirebase.screens.search.BookSearchViewModel
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
            val viewmodel = hiltViewModel<BookSearchViewModel>()
            ReaderBookSearchScreen(navController = navController, viewmodel)
        }
        composable(route = ReaderScreens.DetailsScreens.name + "/{bookId}", arguments = listOf( navArgument("bookId"){
            type = NavType.StringType
            nullable = true
        }

        )){
            BookDetailsScreen(navController = navController, bookId = it.arguments?.getString("bookId"))
        }
        composable(ReaderScreens.UpadateScreen.name){
            BookUpadateScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name){
            ReaderStatsScreen(navController = navController)
        }
    }
}