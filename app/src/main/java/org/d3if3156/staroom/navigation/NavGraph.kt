package org.d3if3156.staroom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3156.staroom.ui.screen.AboutScreen
import org.d3if3156.staroom.ui.screen.DetailScreen
import org.d3if3156.staroom.ui.screen.DeveloperScreen
import org.d3if3156.staroom.ui.screen.KEY_ID_STAR
import org.d3if3156.staroom.ui.screen.MainScreen
import org.d3if3156.staroom.ui.screen.NakostarScreen
import org.d3if3156.staroom.ui.screen.NotificationScreen
import org.d3if3156.staroom.ui.screen.SplashScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormStar.route) {
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_STAR) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_STAR)
            DetailScreen(navController, id)
        }
        composable(route = Screen.Nakostar.route) {
            NakostarScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Notification.route) {
            NotificationScreen(navController)
        }
        composable(route = Screen.Developer.route) {
            DeveloperScreen(navController)
        }
    }
}