package com.mkd.topnewsindia.presentation.navigation

sealed class Route(
    val route: String
) {
    data object HomeScreen : Route(route = "homeScreen")

    data object DetailsScreen : Route(route = "detailsScreen")

    data object WebPage : Route(route = "webPage")

}