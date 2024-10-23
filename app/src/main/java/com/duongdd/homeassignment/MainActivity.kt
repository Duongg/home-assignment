package com.duongdd.homeassignment

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.duongdd.homeassignment.navigation.UserRoutes
import com.duongdd.homeassignment.ui.theme.HomeAssignmentTheme
import com.duongdd.homeassignment.ui.userDetail.UserDetailView
import com.duongdd.homeassignment.ui.userList.UserListView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = UserRoutes.UserListScreen.route
                    ) {
                        composable(route = UserRoutes.UserListScreen.route){
                            UserListView(navController = navController)
                        }
                        composable(
                            route = UserRoutes.UserDetailsScreen.route + "?username={username}",
                            arguments = listOf(
                                navArgument(
                                    name = "username"
                                ) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                            )
                        ){
                            val id = it.arguments?.getString("username") ?: ""
                            UserDetailView(navController, id)
                        }
                    }
                }
            }
        }
    }
}

@HiltAndroidApp
class UserApplication: Application()