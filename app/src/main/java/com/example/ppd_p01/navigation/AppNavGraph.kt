package com.example.ppd_p01.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ppd_p01.UserPreferences
import com.example.ppd_p01.ui.screens.home.HomeScreen
import com.example.ppd_p01.ui.screens.login.LoginScreen
import com.example.ppd_p01.ui.screens.signup.SignUpScreen
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userIdFlow = remember { UserPreferences.getUserId(context).filterNotNull() }
    val userId by userIdFlow.collectAsState(initial = 0)

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
            HomeScreen(navController, userId)
        }
    }
}
