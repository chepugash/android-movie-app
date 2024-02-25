package com.practice.movie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.practice.feature.detail.presentation.DetailScreen

private const val ID = "ID"
private const val DEFAULT_INT_ARG = -1
private const val ID_HARDCODED = 944_098

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
                    defaultValue = DEFAULT_INT_ARG
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getInt(ID)?.let {
                DetailScreen(filmId = ID_HARDCODED, navController = navController)
            }
        }
    }
}