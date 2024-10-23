package com.duongdd.homeassignment.navigation

sealed class UserRoutes(val route: String) {
    object UserListScreen: UserRoutes("user_list_screen")

    object UserDetailsScreen: UserRoutes("user_detail_screen")

}