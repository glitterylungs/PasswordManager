package com.example.passwordmanager.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.passwordmanager.ui.details.PasswordDetails
import com.example.passwordmanager.ui.main.PasswordList
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(
    navController: NavHostController = rememberAnimatedNavController(),
    startDestination: String = NavigationRoutes.PasswordList.route
) {
    AnimatedNavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = NavigationRoutes.PasswordList.route
        ) {
            PasswordList(
                navigateToPasswordDetails = { navController.navigate("${NavigationRoutes.PasswordDetails.route}/passwordId=${it}") }
            )
        }

        composable(
            route = "${NavigationRoutes.PasswordDetails.route}/passwordId={passwordId}",
            arguments = listOf(navArgument("passwordId") { type = NavType.IntType })
        ) {
            PasswordDetails(
                id = requireNotNull(it.arguments).getInt("passwordId"),
                navigateToPreviousScreen = { navController.popBackStack() }
            )
        }
    }
}