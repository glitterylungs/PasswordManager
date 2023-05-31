package com.example.passwordmanager.ui.navigation

sealed class NavigationRoutes(val route: String) {
    object PasswordList : NavigationRoutes("password_list")
    object PasswordDetails : NavigationRoutes("password_details")
}