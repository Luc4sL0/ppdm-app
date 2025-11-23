package com.example.ppd_p01.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ppd_p01.ui.screens.home.HomeScreen
import com.example.ppd_p01.ui.screens.login.LoginScreen
import com.example.ppd_p01.ui.screens.signup.SignUpScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onCreateAccountClick = {
                    navController.navigate(Routes.SIGNUP)
                },
                onSuccessLogin = {
                    navController.navigate(Routes.HOME)
                }
            )
        }

        composable(Routes.SIGNUP) {
            SignUpScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(navController)
        }
    }
}
