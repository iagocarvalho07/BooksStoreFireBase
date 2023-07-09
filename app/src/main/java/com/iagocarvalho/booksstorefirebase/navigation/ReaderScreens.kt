package com.iagocarvalho.booksstorefirebase.navigation

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    CreateAccontScreen,
    ReaderHomeScreen,
    SearchScreen,
    DetailsScreens,
    UpadateScreen,
    ReaderStatsScreen;


    companion object{
        fun FromRoute(route: String): ReaderScreens
        = when(route?.substringBefore("/")){
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccontScreen.name -> CreateAccontScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            SearchScreen.name -> SearchScreen
            DetailsScreens.name -> DetailsScreens
            UpadateScreen.name -> UpadateScreen
            ReaderStatsScreen.name -> ReaderStatsScreen
            null -> ReaderHomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")

        }
    }
}