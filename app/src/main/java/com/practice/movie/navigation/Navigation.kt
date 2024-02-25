package com.practice.movie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.practice.feature.detail.presentation.DetailScreen

private const val ID = "ID"

@Composable
fun Navigation() {

    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = DestinationScreen.DetailScreen.route,
    ) {

        composable(
            route = DestinationScreen.DetailScreen.route,
            arguments = listOf(
                navArgument(ID) {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getInt(ID)?.let {
                DetailScreen(filmId = 944098, navController = navController)
            }
        }
    }
}
