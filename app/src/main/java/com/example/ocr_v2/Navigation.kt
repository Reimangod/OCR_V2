package com.example.ocr_v2

import android.content.res.TypedArray
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import java.security.AccessController.getContext

val example_lst = mutableListOf<RegexData>(
    RegexData("Panadol", 1, 2, 7),
    RegexData("Decolgen", 1, 2, 7),
    RegexData("Khang", 1, 2, 7))

@Composable
fun Navigation() {
    val context = LocalContext.current
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(context as ComponentActivity, navController = navController)
        }
        composable(
            route = Screen.PillScreen.route) {
            TextFieldList(
                    navController = navController,
                )
        }
    }
}