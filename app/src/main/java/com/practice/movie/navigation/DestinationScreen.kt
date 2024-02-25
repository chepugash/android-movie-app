package com.practice.movie.navigation

sealed class DestinationScreen(val route: String) {

    object DetailScreen : DestinationScreen(route = DETAIL_ROUTE)

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
    }
}
