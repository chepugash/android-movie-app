package com.practice.core.common.navigation

sealed class DestinationScreen(val route: String) {

    object DetailScreen : DestinationScreen(route = DETAIL_ROUTE)

    object HomeScreen : DestinationScreen(route = HOME_ROUTE)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    companion object {

        private const val DETAIL_ROUTE = "detail_screen"
        private const val HOME_ROUTE = "home_screen"
    }
}
