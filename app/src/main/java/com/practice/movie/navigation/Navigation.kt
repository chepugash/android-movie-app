package com.practice.movie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.practice.core.common.navigation.DestinationScreen
import com.practice.feature.detail.presentation.DetailScreen
import com.practice.feature.home_impl.presentation.HomeScreen

private const val ID = "ID"
private const val DEFAULT_INT_ARG = -1

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.HomeScreen.route,
    ) {

        composable(
            route = DestinationScreen.HomeScreen.route
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = DestinationScreen.DetailScreen.route + "/{$ID}",
            arguments = listOf(
                navArgument(ID) {
                    type = NavType.IntType
                    defaultValue = DEFAULT_INT_ARG
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getInt(ID)?.let {
                DetailScreen(filmId = it, navController = navController)
            }
        }
    }
}
